package com.example.dao;

import com.example.entity.Movie;

public interface MovieRepo {

	public void addMovie(Movie movie);

	public Movie getMovieById(int id);

}
