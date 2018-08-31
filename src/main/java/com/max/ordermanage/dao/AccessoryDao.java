package com.max.ordermanage.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.ordermanage.domain.Accessory;

@Repository
public interface AccessoryDao extends JpaRepository<Accessory, Long>{

	List<Accessory> findByName(String name);
	
	Page<Accessory> findByName(String name, Pageable pageable);
	
	List<Accessory> findByNameLike(String name);
	
	Page<Accessory> findByNameLike(String name, Pageable pageable);
	
	List<Accessory> findBySpecificationLike(String specification);
	
	Page<Accessory> findBySpecificationLike(String specification, Pageable pageable);
	
	List<Accessory> findByNoteLike(String note);
	
	Page<Accessory> findByNoteLike(String note, Pageable pageable);
}
