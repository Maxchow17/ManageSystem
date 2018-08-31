package com.max.ordermanage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.ordermanage.domain.Country;

@Repository
public interface CountryDao extends JpaRepository<Country, Long> {
	
	Country findByName(String name);

	List<Country> findDistinctNameByNameLike(String name);
	
	List<Country> findTop10DistinctNameByNameLike(String name);
}
