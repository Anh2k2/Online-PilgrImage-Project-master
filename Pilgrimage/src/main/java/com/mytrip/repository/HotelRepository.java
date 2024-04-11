package com.mytrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mytrip.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

}
