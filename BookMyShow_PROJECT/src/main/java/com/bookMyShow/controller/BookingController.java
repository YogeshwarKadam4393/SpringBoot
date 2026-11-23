package com.bookMyShow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookMyShow.entity.BookingDetails;
import com.bookMyShow.service.BookingService;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@GetMapping("/getAllMovies")
	public List getAllMovies() {
		return bookingService.getAllMovieDetails();
	}

	@PostMapping("/bookMyTicket")
	public ResponseEntity<String> bookMyTicket(@RequestBody BookingDetails bookingDetails) {

		String response = bookingService.bookMyTicket(bookingDetails);
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
