package com.example.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "personalInfo")
public class PersonalInfo {

	@ReadOperation
	public Map<String, Object> getPersonalInfo(){
		
		 Map<String, Object> info = new HashMap<String, Object>();
		 info.put("Name", "Yogeshwar Ashok Kadam");
		 info.put("Degree", "Electronics And Telecommunication Engg");
		 info.put("Mobile Number", "8390188135");
		 info.put("Address", "PCMC Pune");
		 
		 return info;
		
	}
	
}
