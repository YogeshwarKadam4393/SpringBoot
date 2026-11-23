package com.exponent;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	public Movie saveMovie(Movie movie) {
		return repository.save(movie);
	}

	public List<Movie> getAllMovies() {
		return repository.findAll();
	}

	public Movie getMovieById(Integer id) {
		return repository.findById(id).orElse(null);
	}

	public Movie updateMovie(int id, Movie movie) {

		Movie existingMovie = repository.findById(id).orElseThrow(() -> new RuntimeException("Movie Not Found"));

		existingMovie.setMovieName(movie.getMovieName());
		existingMovie.setTheatreName(movie.getTheatreName());
		existingMovie.setCity(movie.getCity());
		existingMovie.setPrice(movie.getPrice());
		existingMovie.setAvailableSeats(movie.getAvailableSeats());

		return repository.save(existingMovie);

	}

	public String deleteMovie(Integer id) {
		repository.deleteById(id);
		return "Movie Deleted Successfully";
	}

	public void updateMovie(int movieId, int seats) {

		Movie movie = repository.findById(movieId).orElseThrow(() -> new RuntimeException("No Movie Found"));

		if (seats > movie.getAvailableSeats()) // 12 - 10
		{
			throw new RuntimeException("No Seats Available");
		}

		int updatedSeats = movie.getAvailableSeats() - seats;

		movie.setAvailableSeats(updatedSeats);

		repository.save(movie);

	}

}
