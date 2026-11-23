package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Movie {
	
	@Id
	private int id;
	
	private String movieName;
	
	private double price;

}
