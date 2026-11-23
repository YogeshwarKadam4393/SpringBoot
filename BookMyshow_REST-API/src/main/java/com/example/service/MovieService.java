package com.example.service;

import com.example.entity.Movie;

public interface MovieService {

	public void addMovie(Movie movie);

	public Movie getMovieById(int id);

}
