package com.bookMyShow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookMyShow.entity.BookingDetails;

@Repository
public interface BookingRepo extends JpaRepository<BookingDetails, Integer>{

}
