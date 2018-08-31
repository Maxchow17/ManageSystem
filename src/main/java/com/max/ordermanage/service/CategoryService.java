package com.max.ordermanage.service;

import com.alibaba.fastjson.JSONObject;

public interface CategoryService {

	JSONObject createCategory(JSONObject reqJson);
	
	JSONObject deleteCategory(JSONObject reqJson);
	
	JSONObject updateCategory(JSONObject reqJson);
	
	JSONObject listCategories(JSONObject reqJson);
}
