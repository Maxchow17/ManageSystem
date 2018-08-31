package com.max.ordermanage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.ordermanage.domain.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

	List<Product> findByName(String name);
	
	List<Product> findByNameLike(String name);
	
	List<Product> findBySpecification(String specification);
	
	List<Product> findBySpecificationLike(String specification);
	
	List<Product> findByNote(String note);
	
	List<Product> findByNoteLike(String note);
	
	List<Product> findByCategoryId(Long id);
	
	List<Product> findByCategoryName(String name);
	
	List<Product> findByCategoryNameLike(String name);
	
	List<Product> findByAccessoriesId(Long id);
	
	List<Product> findByAccessoriesNameLike(String name); 
}
