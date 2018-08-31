package com.max.ordermanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.dao.CityDao;
import com.max.ordermanage.dao.CountryDao;
import com.max.ordermanage.domain.City;
import com.max.ordermanage.domain.Country;
import com.max.ordermanage.service.LocationService;
import com.max.ordermanage.util.ErrorEnum;
import com.max.ordermanage.util.JsonUtil;
import com.max.ordermanage.util.Tools;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private CityDao cityDao;

	@Autowired
	private CountryDao countryDao;
	
	@Override
	public JSONObject createLoction(JSONObject reqJson) {
		String city = reqJson.getString("city");
		String country = reqJson.getString("country");
		
		City cityObj = cityDao.findByNameAndCountryName(city, country);
		if (null != cityObj) {
			return JsonUtil.errJson(ErrorEnum.E_5000);
		}
		
		try {
			// city name
			cityObj = new City();
			cityObj.setName(city);
			
			// country
			Country countryObj = countryDao.findByName(country);
			if (null == countryObj) {
				countryObj = new Country();
				countryObj.setName(country);
				countryDao.save(countryObj);
			}
			cityObj.setCountry(countryObj);
			cityDao.save(cityObj);	
			
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	@Override
	public JSONObject updateLocation(JSONObject reqJson) {
		String id = reqJson.getString("id");
		String city = reqJson.getString("city");
		String country = reqJson.getString("country");
		
		try {
			City cityObj = cityDao.getOne(Long.valueOf(id));
			if (null == cityObj) {
				return JsonUtil.operationResult(false);
			}
			cityObj.setName(city);
			
			Country countryObj = countryDao.findByName(country);
			if (null == countryObj) {
				return JsonUtil.operationResult(false);
			}
			cityObj.setCountry(countryObj);
			cityDao.save(cityObj);
			
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	@Override
	public JSONObject deleteLocation(JSONObject reqJson) {
		String id = reqJson.getString("id");
		
		try {
			City city = cityDao.getOne(Long.valueOf(id));
			if (null == city) {
				return JsonUtil.operationResult(false);
			}
			
			cityDao.delete(city);
			
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	@Override
	public JSONObject listLocations(JSONObject reqJson) {
		int currentPage = reqJson.getInteger("currentPage");
		int pageSize = reqJson.getInteger("pageSize");
		
		if (!Tools.verifyPage(currentPage, pageSize)) {
			return JsonUtil.operationResult(false); 
		}
		
		currentPage = Tools.adjustPageNum(currentPage);
		
		Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.Direction.ASC, "CountryName");
		Page<City> cityPage = cityDao.findAll(pageable);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("list", cityPage.getContent());
		resultJson.put("totalCount", cityPage.getTotalElements());
		return JsonUtil.successJson(resultJson);
	}

	@Override
	public JSONObject listCitiesByCountry(JSONObject reqJson) {
		String country = reqJson.getString("country");
		int currentPage = reqJson.getInteger("currentPage");
		int pageSize = reqJson.getInteger("pageSize");
		
		if (!Tools.verifyPage(currentPage, pageSize)) {
			return JsonUtil.operationResult(false);
		}
		
		currentPage = Tools.adjustPageNum(currentPage);
		
		Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.Direction.ASC, "name");
		Page<City> cityPage = cityDao.findByCountryName(country, pageable);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("list", cityPage.getContent());
		resultJson.put("totalCount", cityPage.getTotalElements());
		return JsonUtil.successJson(resultJson);
	}

	@Override
	public JSONObject suggestCitiesByCountry(JSONObject reqJson) {
		String country = reqJson.getString("country");
		String city = reqJson.getString("city");
		
		List<City> cities = cityDao.findTop10ByNameLikeAndCountryNameIs(Tools.fuzzyKeyword(city), country);
		List<String> resultList = new ArrayList<>();
		for (City cityObj : cities) {
			resultList.add(cityObj.getName());
		}
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("list", resultList);
		return JsonUtil.successJson(resultJson);
	}

}
