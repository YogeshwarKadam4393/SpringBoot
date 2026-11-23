package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Movie;
import com.example.service.MovieService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MovieController {

	@Autowired
	public MovieService movieService;

	@Value("${movie.name}")
	public String movieName;

	@GetMapping("/")
	public void getMovieName() {
		System.out.println("Movie Name : " + movieName);
	}

	// 1. Add Movie API

	@PostMapping("/addMovie")
	public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {

		System.out.println("Movie : " + movie);

		movieService.addMovie(movie);

		return new ResponseEntity(movie, HttpStatus.CREATED); // 201 for Create and Insert

	}

	@GetMapping("/getMovieById/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable ("id") int id) {
		
		Movie movie = movieService.getMovieById(id);
		
		if(movie == null) {
			throw new NullPointerException("Movie Cannot be null");
		}
				
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);

	}

	// 3. Login API

	@GetMapping("/login")
	public void login(@RequestParam("username") String username, @RequestParam("password") String password) {

		log.info("Login credential of User's Username : {} ", username);
		log.info("Login credential of User's Password : {} ", password);

	}

}
