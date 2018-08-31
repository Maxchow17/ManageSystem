package com.max.ordermanage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.ordermanage.domain.City;
import com.max.ordermanage.domain.Client;

@Repository
public interface ClientDao extends JpaRepository<Client, Long>{
	
	Client findByName(String name);
	
	List<Client> findByNameLike(String name);
	
	List<Client> findByCity(City city);
	
	List<Client> findByCityName(String city);
	
	List<Client> findByCityCountryName(String country);
}
