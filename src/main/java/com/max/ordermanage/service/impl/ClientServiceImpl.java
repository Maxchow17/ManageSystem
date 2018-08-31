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
import com.max.ordermanage.dao.ClientDao;
import com.max.ordermanage.dao.CountryDao;
import com.max.ordermanage.domain.City;
import com.max.ordermanage.domain.Client;
import com.max.ordermanage.domain.Country;
import com.max.ordermanage.service.ClientService;
import com.max.ordermanage.util.ErrorEnum;
import com.max.ordermanage.util.JsonUtil;
import com.max.ordermanage.util.Tools;

@Service
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private CountryDao countryDao;
	

	// 创建客户
	@Override
	@Transactional
	public JSONObject createClient(JSONObject reqJson) {
		String name = reqJson.getString("name");
		String email = reqJson.getString("email");
		String phone = reqJson.getString("phone");
		String address = reqJson.getString("address");
		JSONObject location = reqJson.getJSONObject("location");
		String city = location.getString("city");
		String country = location.getString("country");
		
		// 查询客户是否已存在
		Client client = clientDao.findByName(name);
		if (null != client) {
			return JsonUtil.errJson(ErrorEnum.E_2000);
		}
		
		// 创建用户
		Client newClient = new Client();
		newClient.setName(name);
		newClient.setPhone(phone);
		newClient.setEmail(email);
		newClient.setAddress(address);
		
		try {
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
			newClient.setCity(cityObj);
			
			clientDao.save(newClient);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	// 删除客户
	@Override
	@Transactional
	public JSONObject deleteClient(JSONObject reqJson) {
		String id = reqJson.getString("id");
		
		Client clientToDelete = clientDao.getOne(Long.valueOf(id));
		if (null == clientToDelete) {
			return JsonUtil.errJson(ErrorEnum.E_2001);
		}
		
		clientDao.delete(clientToDelete);
		return JsonUtil.operationResult(true);
	}

	// 更新客户信息
	@Override
	@Transactional
	public JSONObject updateClient(JSONObject reqJson) {
		String id = reqJson.getString("id");
		String name = reqJson.getString("name");
		String email = reqJson.getString("email");
		String phone = reqJson.getString("phone");
		String address = reqJson.getString("address");
		JSONObject location = reqJson.getJSONObject("location");
		String city = location.getString("city");
		String country = location.getString("country");
		
		try {
			Client clientToUpdate = clientDao.getOne(Long.valueOf(id));
			clientToUpdate.setName(name);
			clientToUpdate.setPhone(phone);
			clientToUpdate.setEmail(email);
			
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
			clientToUpdate.setCity(cityObj);
			
			clientToUpdate.setAddress(address);
			clientDao.save(clientToUpdate);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	// 查找客户
	@Override
	public JSONObject searchClient(JSONObject reqJson) {
		
		String option = reqJson.getString("option");
		String keyword = reqJson.getString("keyword");
		
		List<Client> resultList = new ArrayList<>();
		JSONObject clientList = new JSONObject();
		if (option.equals("name")) {
			// 通过客户名称模糊查询
			resultList = clientDao.findByNameLike(Tools.fuzzyKeyword(keyword));
		} else if (option.equals("country")) {
			// 通过客户所在国家查询
			resultList = clientDao.findByCityCountryName(keyword);
		} else if (option.equals("city")) {
			resultList = clientDao.findByCityName(keyword);
		}
		
		clientList.put("list", resultList);
		return JsonUtil.successJson(clientList);
	}

	@Override
	public JSONObject listClients(JSONObject reqJson) {
		int currentPage = reqJson.getInteger("currentPage");
		int pageSize = reqJson.getInteger("pageSize");
		
		if (!Tools.verifyPage(currentPage, pageSize)) {
			return JsonUtil.errJson(ErrorEnum.E_108);
		}
		
		currentPage = Tools.adjustPageNum(currentPage);
		
		JSONObject resultJson = new JSONObject();
		Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.Direction.ASC, "name");
		Page<Client> clientPage = clientDao.findAll(pageable);
		resultJson.put("list", clientPage.getContent());
		resultJson.put("totalCount", clientPage.getTotalElements());
		
		return JsonUtil.successJson(resultJson);
	}

}
