package com.max.ordermanage.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.ordermanage.domain.Client;
import com.max.ordermanage.domain.SalesOrder;

@Repository
public interface SalesOrderDao extends JpaRepository<SalesOrder, Long>{

	SalesOrder findByName(String name);
	
	List<SalesOrder> findByNameLike(String name);
	
	List<SalesOrder> findByClient(Client client);
	
	List<SalesOrder> findByClientId(Long id);
	
	List<SalesOrder> findByClientName(String clientName);
	
	List<SalesOrder> findByStartDateBetween(Date sd, Date ed);
	
	List<SalesOrder> findByEndDateBetween(Date sd, Date ed);
	
	List<SalesOrder> findByNoteLike(String note);
}
