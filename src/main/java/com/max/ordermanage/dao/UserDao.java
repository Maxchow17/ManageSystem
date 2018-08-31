package com.max.ordermanage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.ordermanage.domain.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>{
	
	List<User> findByName(String name);
	
	User findByNameAndPassword(String name, String password);
}
