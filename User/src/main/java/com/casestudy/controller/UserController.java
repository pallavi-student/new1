package com.casestudy.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.casestudy.model.BookingModel;
import com.casestudy.model.TrainModel;
import com.casestudy.model.UserModel;
import com.casestudy.repository.UserRepository;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/user")
public class UserController {

		@Autowired
		private UserRepository userrepo;
		
		@Autowired
		private RestTemplate restTemplate;
		
		//Adding Logger 
		Logger logger = LoggerFactory.getLogger(UserController.class);
		
		
		
//--------------------------------------User-CRUD-----------------------------------------
		
		//Rest API to get User details by Id
		@GetMapping("/viewuserprofile/{id}")
		public Optional<UserModel> getuser(@PathVariable("id") String id){
			
			//logger implementation
	        logger.info("[viewuserprofile/Id] info message added");
	        logger.debug("[viewuserprofile/Id] debug message added");
	        
			return userrepo.findById(id);
		}

		
		//Rest API to update User details by Id
		@RequestMapping(value="/updateprofile/{id}", method=RequestMethod.PUT)
		public String update(@PathVariable("id") String id, @RequestBody UserModel usermodel) {
			userrepo.save(usermodel);
			
			//logger implementation
	        logger.info("[updateprofile/Id] info message added");
	        logger.debug("[updateprofile/Id] debug message added");
	        
			return "Updated";
		}
	
		
		//Rest API to delete User profile by Id
		@DeleteMapping("/deleteprofile/{id}")
		public String delete(@PathVariable String id) {
			userrepo.deleteById(id);
			
			//logger implementation
	        logger.info("[deleteprofile/Id] info message added");
	        logger.debug("[deleteprofile/Id] debug message added");
	        
			return "User with id "+id+" have been deleted";
		}
		
		
		//Rest API to get all User Details
		@GetMapping("/viewallusers")
		public List<UserModel>getuser(){
			
			//logger implementation
	        logger.info("[viewallusers] info message added");
	        logger.debug("[viewallusers] debug message added");
	        
			return userrepo.findAll();
		}
		
//---------------------------------------User-Train---------------------------------------------
		
		//Rest API to get all Train Details
		@GetMapping("/viewalltrains")
		public List<TrainModel> getAllTrains(){
			
			//logger implementation
	        logger.info("[viewalltrains] info message added");
	        logger.debug("[viewalltrains] debug message added");
	        
			return Arrays.asList(restTemplate.getForObject("http://TrainDetails/train/viewalltrains",TrainModel[].class));
		}
		

		//Rest API to get Train Details by Id
		@GetMapping("/viewtrain/{trainNo}")
		public TrainModel getTrains(@PathVariable("trainNo") String trainNo){
			
			//logger implementation
	        logger.info("[viewtrain/trainId] info message added");
	        logger.debug("[viewtrain/trainId] debug message added");
	        
			return restTemplate.getForObject("http://TrainDetails/train/viewtrainbyno/"+trainNo,TrainModel.class);	
		}
		
		
		//Rest API to get Train details from source to destination
		@GetMapping("/findbw/{trainFrom}/{trainTo}")
		public TrainModel findByloc(@PathVariable("trainFrom") String trainFrom, @PathVariable("trainTo") String trainTo){
			
			//logger implementation
	        logger.info("[findbw/source/destination] info message added");
	        logger.debug("[findbw/source/destination] debug message added");
	        
			return this.restTemplate.getForObject("http://TrainDetails/train/findbw/"+trainFrom+"/"+trainTo,TrainModel.class);
		}
		
		
		//Rest API to get Train fare by Id
		@GetMapping("/findfareno/{trainNo}")
		public int findfare(@PathVariable("trainNo") String trainNo){
			
			//logger implementation
	        logger.info("[findfareno/trainId] info message added");
	        logger.debug("[findfareno/trainId] debug message added");
	        
			return restTemplate.getForObject("http://TrainDetails/train/findfarebyno/"+trainNo,Integer.class);
		}
		
		
		//Rest API to get Train fare by Name
		@GetMapping("/findfarename/{trainName}")
		public int findfarebyname(@PathVariable("trainName") String trainName){
			
			//logger implementation
	        logger.info("[findfarename/trainName] info message added");
	        logger.debug("[findfarename/trainName] debug message added");
	        
			return restTemplate.getForObject("http://TrainDetails/train/findfarebyname/"+trainName,Integer.class);
		}
		
		
		//Rest API to get Train time by Id
		@GetMapping("/findtimeno/{trainNo}")
		public String findtime(@PathVariable("trainNo") String trainNo){
			
			//logger implementation
	        logger.info("[findtimeno/trainId] info message added");
	        logger.debug("[findtimeno/trainId] debug message added");
	        
			return restTemplate.getForObject("http://TrainDetails/train/findtimebyno/"+trainNo,String.class);
		}
		
		
		//Rset API to get Train time by Name
		@GetMapping("/findtimename/{trainName}")
		public String findtimebyname(@PathVariable("trainName") String trainName){
			
			//logger implementation
	        logger.info("[findtimename/trainNmae] info message added");
	        logger.debug("[findtimename/trainNmae] debug message added");
	        
			return restTemplate.getForObject("http://TrainDetails/train/findtimebyname/"+trainName,String.class);
		}		

//-----------------------------------User-Ticket-------------------------------------------------
		
		//Rset API to add Booking details 
		@PostMapping("/bookticket")
		public String bookticket(@RequestBody BookingModel book){
			
			//logger implementation
	        logger.info("[bookticket] info message added");
	        logger.debug("[bookticket] debug message added");
	        
			TrainModel trainModel = restTemplate.getForObject("http://TrainDetails/train/"+book.getTrainNo(),TrainModel.class);
			int fare = trainModel.getFare();
			
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
			String response = restTemplate.postForObject("http://BOOKINGDETAILS/booking/bookticket/"+username+"/"+fare, book,String.class);
//			return seats+"";
			
			return response;
			
		}
		
		
		//Rest API to delete Booking by pnrId
		@DeleteMapping("/cancelticket/{pnrId}")
		public String cancelticket(@PathVariable String pnrId){
			
			BookingModel bookingModel = restTemplate.getForObject("http://BookingDetails/booking/getorderpnr/"+pnrId,BookingModel.class);
			TrainModel trainModel = restTemplate.getForObject("http://TrainDetails/train/viewtrainbyno/"+bookingModel.getTrainNo(),TrainModel.class);
			this.restTemplate.delete("http://BookingDetails/booking/cancelticket/"+pnrId, BookingModel.class);
			restTemplate.postForObject("http://TrainDetails/train/increaseseat/"+trainModel.getTrainNo()+"/"+bookingModel.getTotalseats(),bookingModel, BookingModel.class);
			
			//logger implementation
	        logger.info("[cancelticket/pnrId] info message added");
	        logger.debug("[cancelticket/pnrId] debug message added");
	        
			return "Train Ticket with "+pnrId+" cancelled Succesfully";
		}
		
		
		//UNNECESSARY
		//Rest API to get all Bookings
		@GetMapping("/getallorders")
		public List<BookingModel> getAllOrder(){
			
			//logger implementation
	        logger.info("[getallorders] info message added");
	        logger.debug("[getallorders] debug message added");
	        
			return Arrays.asList(restTemplate.getForObject("http://BookingDetails/booking/getallorders",BookingModel[].class));
		}
		
		
		//Rest API to get Bookings by User Id
		@GetMapping("/getorder/{userId}")
		public List<BookingModel> getorder(@PathVariable("userId") String userId){
			
			//logger implementation
	        logger.info("[getorder/userId] info message added");
	        logger.debug("[getorder/userId] debug message added");
	        
			return Arrays.asList(restTemplate.getForObject("http://BookingDetails/booking/getorder/"+userId,BookingModel[].class));
		}
		
		
		//Rest API to update Booking details for User By User Id
		@PutMapping("/updateorder/{userId}")
		public String updateorder(@PathVariable String userId, @RequestBody BookingModel book) {
			restTemplate.put("http://BookingDetails/booking/updateorder/{userId}",userId,book);
			
			//logger implementation
	        logger.info("[updateorder/userId] info message added");
	        logger.debug("[updateorder/userid] debug message added");
	        
			return "Order with id "+userId+" havebeen updated Successfully";
		}
		
		
	}
