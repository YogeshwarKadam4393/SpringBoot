package com.empMgtSytm.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(value = "qa")
public class QAImple implements PaymentService {

	@Override
	public String paymentGateway() {
		
		return "This is QA Implementation ";
	}

}
