package com.max.ordermanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.dao.CityDao;
import com.max.ordermanage.dao.CountryDao;
import com.max.ordermanage.dao.SupplierDao;
import com.max.ordermanage.domain.City;
import com.max.ordermanage.domain.Country;
import com.max.ordermanage.domain.Supplier;
import com.max.ordermanage.service.SupplierService;
import com.max.ordermanage.util.ErrorEnum;
import com.max.ordermanage.util.JsonUtil;
import com.max.ordermanage.util.Tools;

@Service
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	private SupplierDao supplierDao;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private CountryDao countryDao;

	@Override
	@Transactional
	public JSONObject createSupplier(JSONObject reqJson) {
		String name = reqJson.getString("name");
		String city = reqJson.getString("city");
		String country = reqJson.getString("country");
		// default rate 0.0
		double rate = 0.0;
		
		try {
			// name
			Supplier supplier = new Supplier();
			supplier.setName(name);
			
			// location info
			City cityObj = cityDao.findByNameAndCountryName(city, country);
			if (null == cityObj) {
				// city
				cityObj = new City();
				cityObj.setName(city);
				// country
				Country countryObj = countryDao.findByName(country);
				if (null == countryObj) {
					countryObj = new Country();
					countryDao.save(countryObj);
				}
				cityObj.setCountry(countryObj);
				
				// 存储新实体
				cityDao.save(cityObj);
			}
			supplier.setCity(cityObj);
			
			
			// set default rate to 0.0
			supplier.setRate(rate);
			
			// save supplier to database
			supplierDao.save(supplier);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true); 
	}

	@Override
	@Transactional
	public JSONObject deleteSupplier(JSONObject reqJson) {
		String id = reqJson.getString("id");
		try {
			supplierDao.deleteById(Long.valueOf(id));
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		return JsonUtil.operationResult(true);
	}

	@Override
	@Transactional
	public JSONObject updateSupplier(JSONObject reqJson) {
		String id = reqJson.getString("id");
		String name = reqJson.getString("name");
		String city = reqJson.getString("city");
		String country = reqJson.getString("country");
		double rate = reqJson.getDouble("rate");
		
		Supplier supplier = supplierDao.getOne(Long.valueOf(id));
		if (null == supplier) {
			return JsonUtil.operationResult(false);
		}
		
		try {
			// name
			supplier.setName(name);
			
			// location info
			City cityObj = cityDao.findByNameAndCountryName(city, country);
			if (null == cityObj) {
				cityObj = new City();
				cityObj.setName(city);
				
				Country countryObj = countryDao.findByName(country);
				if (null == countryObj) {
					countryObj = new Country();
					countryObj.setName(country);
				}
				cityObj.setCountry(countryObj);
				cityDao.save(cityObj);
			}
			supplier.setCity(cityObj);
			
			// rate
			supplier.setRate(rate);
			
			supplierDao.save(supplier);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		return JsonUtil.operationResult(true);
	}

	@Override
	public JSONObject searchSupplier(JSONObject reqJson) {
		String option = reqJson.getString("option");
		String keyword = reqJson.getString("keyword");
		
		List<Supplier> resultList = new ArrayList<>();
		
		if (option.equals("name")) {
			resultList = supplierDao.findByNameLike(Tools.fuzzyKeyword(keyword));
		} else if (option.equals("city")) {
			resultList = supplierDao.findByCityName(keyword);
		} else if (option.equals("country")) {
			resultList = supplierDao.findByCityCountryName(keyword);
		}
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("list", resultList);
		return JsonUtil.successJson(resultJson);
	}

	@Override
	public JSONObject listSuppliers(JSONObject reqJson) {
		int currentPage = reqJson.getInteger("currentPage");
		int pageSize = reqJson.getInteger("pageSize");
		
		if (!Tools.verifyPage(currentPage, pageSize)) {
			return JsonUtil.errJson(ErrorEnum.E_108);
		}
		
		currentPage = Tools.adjustPageNum(currentPage);
		
		Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.Direction.ASC, "name");
		Page<Supplier> supplierPage = supplierDao.findAll(pageable);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("list", supplierPage.getContent());
		resultJson.put("totalCount", supplierPage.getTotalElements());
		
		return JsonUtil.successJson(resultJson);
	}
}
