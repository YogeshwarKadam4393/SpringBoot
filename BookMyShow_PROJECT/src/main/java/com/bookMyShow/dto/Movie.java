package com.bookMyShow.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Movie {

	private Integer movieId;

	private String movieName;
	
	private String theatreName;
	
	private String city;
	
	private Double price;
	
	private Integer availableSeats;
	
}
