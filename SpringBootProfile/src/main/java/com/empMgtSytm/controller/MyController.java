package com.empMgtSytm.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empMgtSytm.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MyController {

	@Autowired
	public Environment env;
	
	@Autowired
	public PaymentService paymentService;
	
	@GetMapping("/")
	public String getPaymentGateway() {
		
		log.info("Profile ---> " + Arrays.toString(env.getActiveProfiles()));
		
		return paymentService.paymentGateway();		
	}
	
}
