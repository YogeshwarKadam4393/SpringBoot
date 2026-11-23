package com.bookMyShow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bookMyShow.dto.Movie;
import com.bookMyShow.entity.BookingDetails;
import com.bookMyShow.repository.BookingRepo;

@Service
public class BookingServiceImple implements BookingService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BookingRepo bookingRepo;

	String url = "http://localhost:8081/movie"; // Movie Service Base URL

	@Override
	public List getAllMovieDetails() {

		List listOfMovie = restTemplate.getForObject(url, List.class);

		return listOfMovie;
	}

	@Override
	public String bookMyTicket(BookingDetails bookingDetails) {

		int movieId = bookingDetails.getMovieId();

		ResponseEntity response = restTemplate.getForEntity(url + "/" + movieId, Movie.class);

		Movie movie = (Movie) response.getBody();

		bookingDetails.setMovieName(movie.getMovieName());

		if (bookingDetails.getTotalNumberOfBookings() > movie.getAvailableSeats()) {

			throw new RuntimeException("Seats Not Available");
		}

		bookingRepo.save(bookingDetails);

		// http://localhost:8081/movie/updateSeats/2/5

		restTemplate.put(url + "/updateSeats" + "/" + movieId + "/" + bookingDetails.getTotalNumberOfBookings(), null);

		return "Booking Confirmed";
	}

}
