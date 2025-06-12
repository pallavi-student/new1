package com.casestudy.controller;

import java.util.List;
import java.util.Map;

import com.casestudy.model.*;
import com.casestudy.repository.BookingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingRepository bookingrepo;

	Logger logger = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/bookticket/{userId}/{fare}")
	public ResponseEntity<?> bookticket(@PathVariable String userId,
										@PathVariable int fare,
										@RequestBody BookingModel book) {
		book.setUserId(userId);
		List<BookingModel> ticketslist = bookingrepo.findByUserId(userId);

		int totalseatsBooked = ticketslist.stream().mapToInt(BookingModel::getTotalseats).sum();
		if (totalseatsBooked + book.getTotalseats() > 6) {
			return ResponseEntity.badRequest().body("Booking limit exceeded (max 6 seats per user).");
		}

		// Step 1: Fetch Train Details
		String trainServiceUrl = "http://TrainDetails/train/" + book.getTrainNo();
		TrainModel train = restTemplate.getForObject(trainServiceUrl, TrainModel.class);

		if (train == null || train.getSeats() < book.getTotalseats()) {
			return ResponseEntity.badRequest().body("Not enough seats available.");
		}

		// Step 2: Update train seat count
		train.setSeats(train.getSeats() - book.getTotalseats());
		restTemplate.put("http://TRAINDETAILS/train/updateSeats/" + train.getTrainNo(), train);

		// Step 3: Save booking with PENDING status and correct fare
		book.setFare(book.getTotalseats() * fare); // Correct fare calculation
		book.setStatus(BookingStatus.PENDING);
		bookingrepo.save(book);

		// Step 4: Call payment-service
		PaymentRequest paymentRequest = new PaymentRequest(book.getPnrId(), book.getFare(), book.getEmail());

		ResponseEntity<Map> paymentResponse = restTemplate.postForEntity(
				"http://PAYMENTSERVICE/payment/create-checkout-session",
				paymentRequest,
				Map.class);

		if (paymentResponse.getStatusCode().is2xxSuccessful()) {
			Map<String, Object> responseBody = paymentResponse.getBody();
			if (responseBody != null && responseBody.containsKey("checkoutUrl")) {
				String checkoutUrl = (String) responseBody.get("checkoutUrl");
				String sessionId = (String) responseBody.get("sessionId");

				// Store the sessionId if needed, example:
				// book.setStripeSessionId(sessionId);
				// bookingrepo.save(book);

				// Return checkout URL to frontend for redirect
				return ResponseEntity.ok(Map.of("checkoutUrl", checkoutUrl));
			} else {
				// Payment service responded without checkout URL
				return ResponseEntity.status(500).body("Payment service did not return a checkout URL.");
			}
		} else {
			// Payment failed, cancel booking and restore train seats
			book.setStatus(BookingStatus.CANCELLED);
			bookingrepo.save(book);

			train.setSeats(train.getSeats() + book.getTotalseats());
			restTemplate.put("http://TrainDetails/train/updateSeats/" + train.getTrainNo(), train);

			return ResponseEntity.status(500).body("Payment failed. Booking cancelled.");
		}
	}

	@GetMapping("/getallorders")
	public List<BookingModel> getAllOrders() {
		logger.info("[getallorders] info message");
		return bookingrepo.findAll();
	}

	@GetMapping("/getorder/{userId}")
	public List<BookingModel> getorder(@PathVariable String userId) {
		logger.info("[getorder] for userId: {}", userId);
		return bookingrepo.findByUserId(userId);
	}

	@GetMapping("/getorderpnr/{pnrId}")
	public BookingModel getorderpnr(@PathVariable String pnrId) {
		logger.info("[getorderpnr] for pnrId: {}", pnrId);
		return bookingrepo.findByPnrId(pnrId);
	}

	@DeleteMapping("/cancelticket/{pnrId}")
	public String cancelticket(@PathVariable String pnrId) {
		BookingModel booking = bookingrepo.findByPnrId(pnrId);
		booking.setStatus(BookingStatus.CANCELLED);
		bookingrepo.save(booking);
		bookingrepo.deleteById(pnrId);
		return "Train Ticket with PNR " + pnrId + " cancelled successfully";
	}

	@PatchMapping("/{pnrId}/status")
	public ResponseEntity<String> updateOrderStatus(@PathVariable String pnrId, @RequestParam BookingStatus status) {
		BookingModel booking = bookingrepo.findByPnrId(pnrId);
		booking.setStatus(status);
		bookingrepo.save(booking);
		return ResponseEntity.ok("Order status updated to " + status.name());
	}

	@PostMapping("/update-status/{bookingId}")
	public ResponseEntity<String> updateBookingStatus(@PathVariable String bookingId) {
		BookingModel booking = bookingrepo.findByPnrId(bookingId);
		booking.setStatus(BookingStatus.CONFIRMED);
		bookingrepo.save(booking);
		return ResponseEntity.ok("Status Updated");
	}

}
