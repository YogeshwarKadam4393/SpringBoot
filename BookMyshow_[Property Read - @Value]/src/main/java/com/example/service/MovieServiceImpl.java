package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.MovieRepo;
import com.example.entity.Movie;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	public MovieRepo movieRepo;

	@Override
	public void addMovie(Movie movie) {
		
		if(movie == null) {
			throw new NullPointerException("Movie Cannot Be Null...");
		}
		
		movieRepo.addMovie(movie);
		
	}

	@Override
	public Movie getMovieById(int id) {
		
		Movie movie = movieRepo.getMovieById(id);
		
		if(movie == null) {
			throw new NullPointerException("Movie Cannot Be Null or Movie With Given Id is Not Available");
		}
		
		return movie;
	}
	
	
	
	
}
