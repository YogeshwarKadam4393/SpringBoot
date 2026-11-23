package com.bookMyShow.service;

import java.util.List;

import com.bookMyShow.entity.BookingDetails;

public interface BookingService {

	public List getAllMovieDetails();

	public String bookMyTicket(BookingDetails bookingDetails);

}
