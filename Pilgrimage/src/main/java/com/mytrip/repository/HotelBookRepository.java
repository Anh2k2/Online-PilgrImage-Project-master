package com.mytrip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mytrip.model.HotelBook;
import com.mytrip.model.User;

public interface HotelBookRepository extends JpaRepository<HotelBook, Integer> {

	public HotelBook findByOrderId(String orderId);
	
	public List<HotelBook> findByStatus(String status);
	
	public List<HotelBook> findByUserAndStatus(User user, String status);
	
	public long countByStatus(String status);
}
