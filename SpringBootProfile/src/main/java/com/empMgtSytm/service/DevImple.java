package com.empMgtSytm.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(value = "dev")
public class DevImple implements PaymentService {

	@Override
	public String paymentGateway() {
		
		return "This is Dev Implementation ";
	}

}
