package com.empMgtSytm.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(value = "prod")
public class PRODImple implements PaymentService {

	@Override
	public String paymentGateway() {
		
		return "This is PROD Implementation ";
	}

}
