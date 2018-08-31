package com.max.ordermanage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.dao.ClientDao;
import com.max.ordermanage.dao.ProductDao;
import com.max.ordermanage.dao.QuotationDao;
import com.max.ordermanage.dao.SupplierDao;
import com.max.ordermanage.domain.Client;
import com.max.ordermanage.domain.Product;
import com.max.ordermanage.domain.Quotation;
import com.max.ordermanage.domain.Supplier;
import com.max.ordermanage.service.QuotationService;
import com.max.ordermanage.util.ErrorEnum;
import com.max.ordermanage.util.JsonUtil;
import com.max.ordermanage.util.Tools;

@Service
public class QuotationServiceImpl implements QuotationService {

	@Autowired
	private QuotationDao quotationDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private SupplierDao supplierDao;
	
	@Override
	@Transactional
	public JSONObject createQuotation(JSONObject reqJson) {
		
		String name = reqJson.getString("name");
		String productId = reqJson.getString("productId");
		String clientId = reqJson.getString("clientId");
		String supplierId = reqJson.getString("supplierId");
		String salesPrice = reqJson.getString("salesPrice");
		String purchasePrice = reqJson.getString("purchasePrice");
		String number = reqJson.getString("number");
		String note = reqJson.getString("note");
		
		try {
			Quotation quotation = new Quotation();
			// name
			quotation.setName(name);
			
			// product
			Product product = productDao.getOne(Long.valueOf(productId));
			quotation.setProduct(product);
			
			// client
			Client client = clientDao.getOne(Long.valueOf(clientId));
			quotation.setClient(client);
			
			// supplier
			Supplier supplier = supplierDao.getOne(Long.valueOf(supplierId));
			quotation.setSupplier(supplier);
			
			// sales price
			quotation.setSalesPrice(Double.valueOf(salesPrice));
			
			// purchase price
			quotation.setPurchasePrice(Double.valueOf(purchasePrice));
			
			// number
			quotation.setNumber(Integer.valueOf(number));
			
			// date
			quotation.setDate(new Date());
			
			// note 
			quotation.setNote(note);
			
			// insert into database
			quotationDao.save(quotation);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		
		return JsonUtil.operationResult(true);
	}

	@Override
	@Transactional
	public JSONObject deleteQuotation(JSONObject reqJson) {
		String id = reqJson.getString("id");
		try {
			quotationDao.deleteById(Long.valueOf(id));
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		return JsonUtil.operationResult(true);
	}

	@Override
	@Transactional
	public JSONObject updateQuotation(JSONObject reqJson) {
		
		String id = reqJson.getString("id");
		String name = reqJson.getString("name");
		String productId = reqJson.getString("productId");
		String clientId = reqJson.getString("clientId");
		String supplierId = reqJson.getString("supplierId");
		double salesPrice = reqJson.getDouble("salesPrice");
		double purchasePrice = reqJson.getDouble("purchasePrice");
		int number = reqJson.getInteger("number");
		String date = reqJson.getString("date");
		String note = reqJson.getString("note");
		
		try {
			Quotation quotation = quotationDao.getOne(Long.valueOf(id));
			// name
			quotation.setName(name);
			
			// product
			Product product = productDao.getOne(Long.valueOf(productId));
			quotation.setProduct(product);
			
			// client
			Client client = clientDao.getOne(Long.valueOf(clientId));
			quotation.setClient(client);
			
			// supplier
			Supplier supplier = supplierDao.getOne(Long.valueOf(supplierId));
			quotation.setSupplier(supplier);
			
			// sales price
			quotation.setSalesPrice(salesPrice);
			
			// purchase price
			quotation.setPurchasePrice(purchasePrice);
			
			// number
			quotation.setNumber(number);
			
			// date
			quotation.setDate(Tools.stringToDate(date));
			
			// note 
			quotation.setNote(note);
			
			// update to database
			quotationDao.save(quotation);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	@Override
	public JSONObject searchQuotation(JSONObject reqJson) {
		String option = reqJson.getString("option");
		String keyword = reqJson.getString("keyword");
		
		List<Quotation> resultList = new ArrayList<>();
		
		if (option.equals("name")) {
			resultList = quotationDao.findByNameLike(Tools.fuzzyKeyword(keyword));
		} else if (option.equals("productId")) {
			resultList = quotationDao.findByProductId(Long.valueOf(keyword));
		} else if (option.equals("productName")) {
			resultList = quotationDao.findByProductName(keyword);
		} else if (option.equals("clientId")) {
			resultList = quotationDao.findByClientId(Long.valueOf(keyword));
		} else if (option.equals("clientName")) {
			resultList = quotationDao.findByClientName(keyword);
		} else if (option.equals("supplierId")) {
			resultList = quotationDao.findBySupplierId(Long.valueOf(keyword));
		} else if (option.equals("supplierName")) {
			resultList = quotationDao.findBySupplierName(keyword);
		} else if (option.equals("date")) {
			resultList = quotationDao.findByDate(Tools.stringToDate(keyword));
		}
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("list", resultList);
		return JsonUtil.successJson(resultJson);
	}

	@Override
	public JSONObject searchByDateRange(JSONObject reqJson) {
		String startDate = reqJson.getString("startDate");
		String endDate = reqJson.getString("endDate");
		
		List<Quotation> resultList = 
				quotationDao.findByDateBetween(Tools.stringToDate(startDate), Tools.stringToDate(endDate));
		JSONObject resultJson = new JSONObject();
		resultJson.put("list", resultList);
		return JsonUtil.successJson(resultJson);
	}

	@Override
	@Transactional
	public JSONObject updateSalesPrice(JSONObject reqJson) {
		String id = reqJson.getString("id");
		double newSalesPrice = reqJson.getDouble("salesPrice");
		
		// get quotation from database by id
		Quotation quotation = quotationDao.getOne(Long.valueOf(id));
		if (null == quotation) {
			return JsonUtil.operationResult(false);
		}
		
		try {
			quotation.setSalesPrice(newSalesPrice);
			quotationDao.save(quotation);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		return JsonUtil.operationResult(true);
	}

	@Override
	@Transactional
	public JSONObject updatePurchasePrice(JSONObject reqJson) {
		String id = reqJson.getString("id");
		double newPurchasePrice = reqJson.getDouble("purchasePrice");
		
		// get quotation from database by id
		Quotation quotation = quotationDao.getOne(Long.valueOf(id));
		if (null == quotation) {
			return JsonUtil.operationResult(false);
		}
		
		try {
			quotation.setPurchasePrice(newPurchasePrice);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		return JsonUtil.operationResult(true);
	}

	@Override
	public JSONObject listQuotations(JSONObject reqJson) {
		int currentPage = reqJson.getInteger("currentPage");
		int pageSize = reqJson.getInteger("pageSize");
		
		if (!Tools.verifyPage(currentPage, pageSize)) {
			return JsonUtil.errJson(ErrorEnum.E_108);
		}
		
		currentPage = Tools.adjustPageNum(currentPage);
		
		Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.Direction.DESC, "date");
		Page<Quotation> quotationPage = quotationDao.findAll(pageable);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("list", quotationPage.getContent());
		resultJson.put("totalCount", quotationPage.getTotalElements());
		
		return JsonUtil.successJson(resultJson);
	}
	
	
}
