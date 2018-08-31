package com.max.ordermanage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.ordermanage.domain.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {

	Category findByName(String name);
	
	List<Category> findByNameLike(String name);
	
	List<Category> findByCode(String code);
}
