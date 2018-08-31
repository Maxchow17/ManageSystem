package com.max.ordermanage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	private Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@PostMapping("/create")
	public JSONObject createCategory(@RequestBody JSONObject reqJson) {
		logger.info("createCategory: " + reqJson);
		return categoryService.createCategory(reqJson);
	}
	
	@PostMapping("/update")
	public JSONObject updateCategory(@RequestBody JSONObject reqJson) {
		logger.info("updateCategory: " + reqJson);
		return categoryService.updateCategory(reqJson);
	}
	
	@DeleteMapping("/delete")
	public JSONObject deleteCategory(@RequestBody JSONObject reqJson) {
		logger.info("deleteCategory: " + reqJson);
		return categoryService.deleteCategory(reqJson);
	}
	
	@GetMapping("/list")
	public JSONObject listCategory(@RequestParam String currentPage, @RequestParam String pageSize) {
		logger.info("listCategory");
		
		int nCurrentPage = Integer.valueOf(currentPage);
		int nPageSize = Integer.valueOf(pageSize);
		
		JSONObject reqJson = new JSONObject();
		reqJson.put("currentPage", nCurrentPage);
		reqJson.put("pageSize", nPageSize);
		return categoryService.listCategories(reqJson);
	}
}
