package com.casestudy.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.casestudy.model.AdminModel;
import com.casestudy.model.TrainModel;
import com.casestudy.model.UserModel;
import com.casestudy.repository.AdminRepository;
import com.casestudy.repository.TrainRepository;
import com.casestudy.repository.UserRepository;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/admin")
public class AdminController {
	
	    @Autowired
	    private RestTemplate restTemplate;

		@Autowired
		private AdminRepository adminrepo;
		
		@Autowired
		private TrainRepository trainrepo;
		
		@Autowired
		private UserRepository userrepo;
		
		//Adding Logger 
		Logger logger = LoggerFactory.getLogger(AdminController.class);
		
//--------------------------------------Admin-CRUD-----------------------------------------
		
		//Rest API to add Admin details
		@PostMapping("/registeradmin")
		public String addadmin(@RequestBody AdminModel admin) {
			adminrepo.save(admin);
			
			//logger implementation
	        logger.info("[registeradmin] info message added");
	        logger.warn("[registeradmin] warn message added");
	        logger.debug("registeradmin] debug message added");
	        logger.trace("[registeradmin] trace message added");
	        
			return "Admin with Id: "+admin.getId()+" have been Registered Successfully";
		}
		
		
		//Rest API to get Admin details by Id
		@GetMapping("/viewadminprofile/{id}")
		public Optional<AdminModel> getadmin(@PathVariable("id") String id){
			
			//logger implementation
	        logger.info("[viewadminprofile/id] info message added");
	        logger.debug("[viewadminprofile/id] debug message added");
        
			return adminrepo.findById(id);
		}
		
		//Rest API to update Admin details by Id
		@PutMapping("/updateprofile/{id}")
		public String updateadmin(@PathVariable("id") String id, @RequestBody AdminModel adminmodel) {
			adminrepo.save(adminmodel);
			
			//logger implementation
	        logger.info("[updateprofile/id] info message added");
	        logger.debug("[updateprofile/id] debug message added");
	        
			return "Admin with id "+id+" have been updated successfully";
		}
		
		
		//Rest API to delete Admin details by Id
		@DeleteMapping("/deleteadmin/{id}")
		public String deleteadmin(@PathVariable String id) {
			adminrepo.deleteById(id);
			
			//logger implementation
	        logger.info("[deleteadmin/id] info message added");
	        logger.debug("[deleteadmin/id] debug message added");
	        
			return "Admin with id "+id+" have been deleted";
		}
		
		
//---------------------------------------Admin-User----------------------------------------------------
		
	    //Rest API to get all User details 
		@GetMapping("/viewallusers")
		public List<UserModel>getallusers() {
			
			//logger implementation
	        logger.info("[viewallusers] info message added");
	        logger.debug("[viewallusers] debug message added");
	        
			return Arrays.asList(restTemplate.getForObject("http://UserDetails/user/viewallusers",UserModel[].class));
		}
		
		
		//Rest API to get User details by Id
		@GetMapping("/viewuser/{id}")
		public List<UserModel> getuser(@PathVariable("id") String id){
			
			//logger implementation
	        logger.info("[viewuser/id] info message added");
	        logger.debug("[viewuser/id] debug message added");
	        
			return Arrays.asList(restTemplate.getForObject("http://UserDetails/user/viewuser/"+id,UserModel[].class));	
		}
		
		
		//Rest API to update User details by Id
		@PutMapping("/updateuser/{id}")
		public String updateuser(@RequestBody UserModel usermodel, @PathVariable("id") String id) {
			this.restTemplate.put("http://UserDetails/user/updateprofile/{id}",id,usermodel);
			
			//logger implementation
	        logger.info("[viewallusers/id] info message added");
	        logger.debug("[viewallusers/id] debug message added");
	        
			return "User with id : "+id+" have been updated";
		}
		
	
		//Rest API to delete User details by Id
		@DeleteMapping("/deleteuser/{id}")
		public String deleteuser(@PathVariable String id) {
			this.restTemplate.delete("http://UserDetails/user/deleteprofile/{id}",id);
			
			//logger implementation
	        logger.info("[deleteuser/id] info message added");
	        logger.debug("[deleteuser/id] debug message added");
	        
			return "User with id :"+id+" have been deleted";
		}
		
		
		
//--------------------------------------Admin-Train---------------------------------------------
		
	   //Rest API to add Train details
		@PostMapping("/addtrain")
		public String addtrain(@RequestBody TrainModel trainmodel) {
			this.restTemplate.postForObject("http://TrainDetails/train/addtrain", trainmodel, TrainModel.class);
			
			//logger implementation
	        logger.info("[addtrain] info message added");
	        logger.debug("[addtrain] debug message added");
	        
			return "Train with No: "+trainmodel.getTrainNo()+" have been added Successfully";
		}
		
		
	    //Rest API to get all Train details 
		@GetMapping("/viewalltrains")
		public List<TrainModel> getAllTrains(){
			
			//logger implementation
	        logger.info("[viewalltrains] info message added");
	        logger.debug("[viewalltrains] debug message added");
	        
			return Arrays.asList(restTemplate.getForObject("http://TrainDetails/train/viewalltrains",TrainModel[].class));
		}

		
		//Rest API to get User Train by Id
		@GetMapping("/viewalltrains/{trainNo}")
		public TrainModel getTrains(@PathVariable("trainNo") String trainNo){
			
			//logger implementation
	        logger.info("[viewalltrains/id] info message added");
	        logger.debug("[viewalltrains/id] debug message added");
	        
			return restTemplate.getForObject("http://TrainDetails/train/viewtrainbyno/"+trainNo,TrainModel.class);	
		}
		
		
		//Rest API to get User details by Name
		@GetMapping("/viewtrainbyname/{trainName}")
		public TrainModel getTrainsbyname(@PathVariable("trainName") String trainName){

			//logger implementation
	        logger.info("[viewtrainbyname/Name] info message added");
	        logger.debug("[viewtrainbyname/Name] debug message added");
			
			return restTemplate.getForObject("http://TrainDetails/train/viewtrainbyname/"+trainName,TrainModel.class);	
		}
		
		
		//Rest API to update Train details by Id
		@PutMapping("/updatetrain/{trainNo}")
		public String updatetrain(@RequestBody TrainModel trainmodel, @PathVariable String trainNo) {
			this.restTemplate.put("http://TrainDetails/train/updatetrain/{trainNo}",trainmodel,trainNo,TrainModel.class);
			
			//logger implementation
	        logger.info("[updatetrain/id] info message added");
	        logger.debug("[updatetrain/id] debug message added");
			
			return "Train with no. : "+trainNo+" have been updated";
		}
		
	
		//Rest API to delete User details by Id
		@DeleteMapping("/deletetrain/{trainNo}")
		public String deletetrain(@PathVariable String trainNo) {
			this.restTemplate.delete("http://TrainDetails/train/deletetrain/"+trainNo,TrainModel.class);
			
			//logger implementation
	        logger.info("[deletetrain/id] info message added");
	        logger.debug("[deletetrain/id] debug message added");
	        
			return "Train with no. :"+trainNo+" have been deleted";
		}
		
}
