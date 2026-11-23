package com.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.entity.Movie;

@Repository
public class MovieRepoImpl implements MovieRepo{

	@Autowired
	public SessionFactory sf;
	
	@Override
	public void addMovie(Movie movie) {
		
		Session session = sf.openSession();
	    Transaction tx = session.beginTransaction();
	    
	    session.save(movie);
	    
	    tx.commit();
	    System.out.println("Employee saved Successfully...");
		
	}

	@Override
	public Movie getMovieById(int id) {

		Session session = sf.openSession();

		Movie movie = session.get(Movie.class, id);
		
		return movie;

		
	}

}
