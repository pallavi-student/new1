package com.casestudy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.model.AdminModel;
import com.casestudy.model.AuthenticationRequest;
import com.casestudy.model.AuthenticationResponse;
import com.casestudy.repository.AdminRepository;
import com.casestudy.service.JwtUtil;
import com.casestudy.service.UserInfoService;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/admin")
public class AuthController {
	
	@Autowired
	AdminRepository adminrepo;
	
	@Autowired
	AuthenticationManager authenticationmanager;
	
	@Autowired
	UserInfoService userinfoservice;
	
	@Autowired
	JwtUtil jwtutil;
	
	//Adding Logger 
	Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	
    //Rest API to add/Register as Admin details
	@PostMapping("/register")
	private ResponseEntity<AuthenticationResponse> registerClientToken(@RequestBody AuthenticationRequest authrequest){

		AdminModel usermodel =new AdminModel();

		usermodel.setUsername(authrequest.getUsername());
		usermodel.setPassword(authrequest.getPassword());
		
		try {
			adminrepo.save(usermodel);
		}
		catch(Exception e){
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					("Registration Failed") , HttpStatus.OK);
		}
		
		//logger implementation
        logger.info("[register] info message added");
        logger.debug("[register] debug message added");
		
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
				(authrequest.getUsername()+" registered Successfully "), HttpStatus.OK);
	}
	
	//Rest API to authenticate Admin details
	@PostMapping("/authenticate")
	private ResponseEntity<?> authenticateClientToken(@RequestBody AuthenticationRequest authrequest) throws Exception{

		try {
			authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));
		}
		catch(Exception e) {
			return ResponseEntity.ok(new AuthenticationResponse("no"));
		}
		
		UserDetails userdetails= userinfoservice.loadUserByUsername(authrequest.getUsername());
		
		String jwt = jwtutil.generateToken(userdetails);
		
		//logger implementation
        logger.info("[authenticate] info message added");
        logger.debug("[authenticate] debug message added");
        
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	
	
	

	
	
	
	
	
	@GetMapping("/hello")
	public String firstPage() {
		return "Hello World";
	}
	
	@PostMapping("/reg")
	public String adduser(@RequestBody AdminModel adminmodel) {
		adminrepo.save(adminmodel);
		return "User with Id: "+adminmodel.getId()+" have been Registered Successfully";

	}
}
