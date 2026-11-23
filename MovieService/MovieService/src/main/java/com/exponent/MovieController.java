package com.exponent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieService service;

	@PostMapping
	public Movie saveMovie(@RequestBody Movie movie) {
		return service.saveMovie(movie);
	}

	@GetMapping
	public ResponseEntity<List<Movie>> getAllMovies() {
		return new ResponseEntity<List<Movie>>(service.getAllMovies(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Movie getMovieById(@PathVariable Integer id) {
		return service.getMovieById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateMovie(@PathVariable("id") int id, @RequestBody Movie movie) {

		Movie updatedMovie = service.updateMovie(id, movie);

		return new ResponseEntity<>(updatedMovie, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public String deleteMovie(@PathVariable Integer id) {
		return service.deleteMovie(id);
	}

	@PutMapping("/updateSeats/{movieId}/{seats}")
	public void updateSeatDetails(@PathVariable("movieId") int movieId, @PathVariable("seats") int seats) {
		service.updateMovie(movieId, seats);
	}

}
