package com.example.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component 
@Endpoint(id = "exponent")
public class Batch72 {
	
	@ReadOperation
	public Map<String, Object> getMapping(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("batch1", "Java");
		map.put("batch2", "Python");
		map.put("Director", "Mr.Rushant Kangule Sir");
		
		return map;
	}

}
