package com.example.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "resume")
public class Resume {

	@ReadOperation
	public Map<String, Object> getResume(){
		
		 Map<String, Object> resume = new HashMap<String, Object>();
		 resume.put("Language", "Java");
		 resume.put("Hobby", "Karate");
		 resume.put("Experience", "1+ Years");
		 
		 return resume;
		
	}
	
}
