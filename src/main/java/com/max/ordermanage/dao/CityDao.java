package com.max.ordermanage.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.ordermanage.domain.City;

@Repository
public interface CityDao extends JpaRepository<City, Long> {

	List<City> findByName(String name);
	
	City findByNameAndCountryName(String city, String country);
	
	Page<City> findByCountryName(String country, Pageable pageable);
	
	List<City> findDistinctByNameLike(String city);
	
	List<City> findTop10ByNameLikeAndCountryNameIs(String city, String country);
}
