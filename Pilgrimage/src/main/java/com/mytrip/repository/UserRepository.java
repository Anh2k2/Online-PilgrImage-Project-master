package com.mytrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mytrip.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

}
