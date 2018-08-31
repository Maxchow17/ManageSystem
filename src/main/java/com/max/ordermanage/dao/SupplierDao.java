package com.max.ordermanage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.ordermanage.domain.City;
import com.max.ordermanage.domain.Supplier;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Long> {

	Supplier findByName(String name);
	
	List<Supplier> findByNameLike(String name);
	
	List<Supplier> findByCity(City city);
	
	List<Supplier> findByCityName(String city);
	
	List<Supplier> findByCityCountryName(String country);
}
