package com.max.ordermanage.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.max.ordermanage.domain.Quotation;

@Repository
public interface QuotationDao extends JpaRepository<Quotation, Long> {

	Quotation findByName(String name);
	
	List<Quotation> findByNameLike(String name);
	
	List<Quotation> findByClientId(Long id);
	
	List<Quotation> findByClientName(String clientName);
	
	List<Quotation> findByProductId(Long id);
	
	List<Quotation> findByProductName(String productName);
	
	List<Quotation> findBySupplierId(Long id);
	
	List<Quotation> findBySupplierName(String supplierName);
	
	List<Quotation> findByDate(Date date);
	
	List<Quotation> findByDateBetween(Date startDate, Date endDate);
	
	List<Quotation> findByNoteLike(String note);
	
	List<Quotation> findByNoteContains(String keyword);
}
