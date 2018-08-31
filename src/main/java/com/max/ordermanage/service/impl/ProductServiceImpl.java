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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.dao.AccessoryDao;
import com.max.ordermanage.dao.CategoryDao;
import com.max.ordermanage.dao.ProductDao;
import com.max.ordermanage.domain.Accessory;
import com.max.ordermanage.domain.Category;
import com.max.ordermanage.domain.Product;
import com.max.ordermanage.service.ProductService;
import com.max.ordermanage.util.ErrorEnum;
import com.max.ordermanage.util.JsonUtil;
import com.max.ordermanage.util.Tools;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private AccessoryDao accessoryDao;
	
	@Override
	@Transactional
	public JSONObject createProduct(JSONObject reqJson) {
		
		System.out.println("reqJson: " + reqJson);
		
		String name = reqJson.getString("name");
		String categoryId = reqJson.getString("categoryId");
		List<String> accessoriesJsonArray = reqJson.getJSONArray("accessoryIds").toJavaList(String.class);
		String imgUrl = reqJson.getString("imgUrl");
		String specification = reqJson.getString("specification");
		String note = reqJson.getString("note");
		
		try {
			Product product = new Product();
			// name
			product.setName(name);
			
			// category
			Category category = categoryDao.getOne(Long.valueOf(categoryId));
			product.setCategory(category);
			
			// accessories (list)
			List<Accessory> accessories = new ArrayList<>();
			for (String accessoryId : accessoriesJsonArray) {
				Accessory accessory = accessoryDao.getOne(Long.valueOf(accessoryId));
				accessories.add(accessory);
			}
			product.setAccessories(accessories);
			
			// imgUrl
			product.setImgUrl(imgUrl);
			
			// specification
			product.setSpecification(specification);
			
			// note
			product.setNote(note);
			
			// save to database
			productDao.save(product);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	@Override
	@Transactional
	public JSONObject deleteProduct(JSONObject reqJson) {
		String id = reqJson.getString("id");
		try {
			productDao.deleteById(Long.valueOf(id));
		} catch (Exception e) {
			JsonUtil.operationResult(false);
		}
		return JsonUtil.operationResult(true);
	}

	@Override
	@Transactional
	public JSONObject updateProduct(JSONObject reqJson) {
		
		String id = reqJson.getString("id");
		String name = reqJson.getString("name");
		String categoryId = reqJson.getString("categoryId");
		JSONArray accessoriesJsonArray = reqJson.getJSONArray("acessoryIds");
		String imgUrl = reqJson.getString("imgUrl");
		String specification = reqJson.getString("specification");
		String note = reqJson.getString("note");
		
		try {
			Product product = productDao.getOne(Long.valueOf(id));
			if (null == product) {
				return JsonUtil.errJson(ErrorEnum.E_7001);
			}
			
			// name
			product.setName(name);
			
			// category
			Category category = categoryDao.getOne(Long.valueOf(categoryId));
			product.setCategory(category);
			
			// accessories
			List<Accessory> accessories = new ArrayList<>();
			for (Object accessoryId : accessoriesJsonArray) {
				Accessory accessory = accessoryDao.getOne(Long.valueOf(accessoryId.toString()));
				accessories.add(accessory);
			}
			product.setAccessories(accessories);
			
			// imgUrl
			product.setImgUrl(imgUrl);
			
			// specification
			product.setSpecification(specification);
			
			// note
			product.setNote(note);
			
			// save to database
			productDao.save(product);
			
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	@Override
	public JSONObject searchProduct(JSONObject reqJson) {
		String option = reqJson.getString("option");
		String keyword = reqJson.getString("keyword");
		
		List<Product> resultList = new ArrayList<>();
		JSONObject resultJson = new JSONObject();
		
		if (option.equals("name")) {
			resultList = productDao.findByNameLike(Tools.fuzzyKeyword(keyword));
		} else if(option.equals("categoryId")) {
			resultList = productDao.findByCategoryId(Long.valueOf(keyword));
		} else if (option.equals("categoryName")) {
			resultList = productDao.findByCategoryName(keyword);
		} else if (option.equals("specification")) {
			resultList = productDao.findBySpecificationLike(Tools.fuzzyKeyword(keyword));
		} else if (option.equals("note")) {
			resultList = productDao.findByNoteLike(Tools.fuzzyKeyword(keyword));
		} else if (option.equals("accessoryId")) {
			resultList = productDao.findByAccessoriesId(Long.valueOf(keyword));
		}
		
		resultJson.put("list", resultList);
		return JsonUtil.successJson(resultJson);
	}

	@Override
	public JSONObject listProducts(JSONObject reqJson) {
		int currentPage = reqJson.getInteger("currentPage");
		int pageSize = reqJson.getInteger("pageSize");
		
		if (!Tools.verifyPage(currentPage, pageSize)) {
			return JsonUtil.errJson(ErrorEnum.E_108);
		}
		
		currentPage = Tools.adjustPageNum(currentPage);
		
		JSONObject resultJson = new JSONObject();
		Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.Direction.ASC, "name");
		Page<Product> productPage = productDao.findAll(pageable);
		resultJson.put("list", productPage.getContent());
		resultJson.put("totalCount", productPage.getTotalElements());
		
		return JsonUtil.successJson(resultJson);
	}
	
}
