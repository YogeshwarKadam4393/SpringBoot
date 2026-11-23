package com.empMgtSytm.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(value = "uat")
public class UATImple implements PaymentService {

	@Override
	public String paymentGateway() {
		
		return "This is UAT Implementation ";
	}

}
