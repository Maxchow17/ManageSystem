package com.max.ordermanage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.dao.CategoryDao;
import com.max.ordermanage.domain.Category;
import com.max.ordermanage.service.CategoryService;
import com.max.ordermanage.util.ErrorEnum;
import com.max.ordermanage.util.JsonUtil;
import com.max.ordermanage.util.Tools;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	// 创建类型
	@Override
	@Transactional
	public JSONObject createCategory(JSONObject reqJson) {
		String name = reqJson.getString("name");
		String code = reqJson.getString("code");
		String note = reqJson.getString("note");
		
		try {
			Category category = new Category();
			category.setName(name);
			category.setCode(code);
			category.setNote(note);
			categoryDao.save(category);	
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	// 删除类型
	@Override
	@Transactional
	public JSONObject deleteCategory(JSONObject reqJson) {
		String id = reqJson.getString("id");
		try {
			categoryDao.deleteById(Long.valueOf(id));
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		return JsonUtil.operationResult(true);
	}

	// 更新类型信息
	@Override
	@Transactional
	public JSONObject updateCategory(JSONObject reqJson) {
		String id = reqJson.getString("id");
		String name = reqJson.getString("name");
		String code = reqJson.getString("code");
		String note = reqJson.getString("note");
		
		Category category = categoryDao.getOne(Long.valueOf(id));
		if (null == category) {
			return JsonUtil.errJson(ErrorEnum.E_3001);
		}
		
		try {
			category.setName(name);
			category.setCode(code);
			category.setNote(note);
			categoryDao.save(category);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	@Override
	public JSONObject listCategories(JSONObject reqJson) {
		int currentPage = reqJson.getInteger("currentPage");
		int pageSize = reqJson.getInteger("pageSize");
		
		// 判断页码信息正确性
		if (!Tools.verifyPage(currentPage, pageSize)) {
			return JsonUtil.errJson(ErrorEnum.E_108);
		}

		// 页码从零算起
		currentPage = Tools.adjustPageNum(currentPage);
		
		JSONObject resultJson = new JSONObject();
		Pageable pageable = PageRequest.of(currentPage, pageSize);
		Page<Category> categoryPage = categoryDao.findAll(pageable);
		resultJson.put("list", categoryPage.getContent());
		resultJson.put("totalCount", categoryPage.getTotalElements());
		return JsonUtil.successJson(resultJson);
	}
	
}
