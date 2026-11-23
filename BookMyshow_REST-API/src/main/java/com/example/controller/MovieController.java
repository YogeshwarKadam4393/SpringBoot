package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Movie;
import com.example.service.MovieService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MovieController {
	
	@Autowired
	public MovieService movieService;
	
	// 1. Add Movie API
	
	@PostMapping("/addMovie")
	public void addMovie(@RequestBody Movie movie) {
		
		System.out.println("Movie : " + movie);
		
		movieService.addMovie(movie);
		
	}
	
	// 2. Get Movie By Id API
	
	@GetMapping("/getMovieById/{id}")
	public Movie getMovieById(@PathVariable ("id") int id) {
		
		System.out.println("Movie Id : " + id);
		
		Movie movie = movieService.getMovieById(id);
		
		System.out.println(movie);
		
		return movie;
		
	}
	
	// 3. Login API
	
	@GetMapping("/login")
	public void login(@RequestParam ("username") String username,
			          @RequestParam ("password") String password) {
		
		log.info("Login credential of User's Username : {} " , username);
		log.info("Login credential of User's Password : {} " , password);

	}

}
