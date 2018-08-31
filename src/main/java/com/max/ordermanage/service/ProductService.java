package com.max.ordermanage.service;

import com.alibaba.fastjson.JSONObject;

public interface ProductService {

	JSONObject createProduct(JSONObject reqJson);
	
	JSONObject deleteProduct(JSONObject reqJson);
	
	JSONObject updateProduct(JSONObject reqJson);
	
	JSONObject searchProduct(JSONObject reqJson);
	
	JSONObject listProducts(JSONObject reqJson);
}
