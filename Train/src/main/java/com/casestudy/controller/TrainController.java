package com.casestudy.controller;

import java.util.List;

import com.casestudy.model.BookingModel;
import com.casestudy.model.TrainModel;
import com.casestudy.repository.TrainRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/train")
public class TrainController {

	@Autowired
	TrainRepository trainrepo;

	Logger logger = LoggerFactory.getLogger(TrainController.class);

	@PostMapping("/addtrain")
	public void addTrain(@RequestBody TrainModel trainmodel) {
		trainrepo.save(trainmodel);
	}

	@GetMapping("/viewalltrains")
	public List<TrainModel> getAllTrains() {
		return trainrepo.findAll();
	}

	@GetMapping("/{trainNo}")
	public TrainModel getTrainByNo(@PathVariable String trainNo) {
		return trainrepo.findByTrainNo(trainNo);
	}

	@PutMapping("/updateSeats/{trainNo}")
	public ResponseEntity<String> updateSeats(@PathVariable String trainNo, @RequestBody TrainModel updatedTrain) {
		TrainModel train = trainrepo.findByTrainNo(trainNo);
		train.setSeats(updatedTrain.getSeats());
		trainrepo.save(train);
		return ResponseEntity.ok("Seats updated for train " + trainNo);
	}

	@GetMapping("/viewtrainbyname/{trainName}")
	public List<TrainModel> getTrainsByName(@PathVariable String trainName) {
		return trainrepo.findByTrainName(trainName);
	}

	@PutMapping("/updatetrain/{trainNo}")
	public String updateTrain(@PathVariable String trainNo, @RequestBody TrainModel trainmodel) {
		trainrepo.save(trainmodel);
		return "Train with no. " + trainNo + " has been updated successfully";
	}

	@DeleteMapping("/deletetrain/{trainNo}")
	public void deleteTrain(@PathVariable String trainNo) {
		TrainModel train = trainrepo.findByTrainNo(trainNo);
		trainrepo.delete(train);
	}

	@GetMapping("/findbw/{trainFrom}/{trainTo}")
	public List<TrainModel> findByLocation(@PathVariable String trainFrom, @PathVariable String trainTo) {
		return trainrepo.findByTrainFromAndTrainTo(trainFrom, trainTo);
	}

	@GetMapping("/findfrom/{trainFrom}")
	public List<TrainModel> findByFrom(@PathVariable String trainFrom) {
		return trainrepo.findByTrainFrom(trainFrom);
	}

	@GetMapping("/findto/{trainTo}")
	public List<TrainModel> findByTo(@PathVariable String trainTo) {
		return trainrepo.findByTrainTo(trainTo);
	}

	@GetMapping("/findfarebyno/{trainNo}")
	public int findFareByNo(@PathVariable String trainNo) {
		return trainrepo.findByTrainNo(trainNo).getFare();
	}

	@PostMapping("/decreaseseat/{trainNo}/{seats}")
	public void decreaseSeats(@PathVariable String trainNo, @PathVariable int seats) {
		TrainModel train = trainrepo.findByTrainNo(trainNo);
		train.setSeats(train.getSeats() - seats);
		trainrepo.save(train);
	}

	@PostMapping("/increaseseat/{trainNo}/{seats}")
	public void increaseSeats(@PathVariable String trainNo, @PathVariable int seats) {
		TrainModel train = trainrepo.findByTrainNo(trainNo);
		train.setSeats(train.getSeats() + seats);
		trainrepo.save(train);
	}

	@GetMapping("/findtimebyno/{trainNo}")
	public String findTimeByNo(@PathVariable String trainNo) {
		return trainrepo.findByTrainNo(trainNo).getTime();
	}

	@GetMapping("/findtimebyname/{trainName}")
	public String findTimeByName(@PathVariable String trainName) {
		return trainrepo.findByTrainNo(trainName).getTime();
	}
}
