package com.max.ordermanage.service;

import com.alibaba.fastjson.JSONObject;

public interface SupplierService {

	JSONObject createSupplier(JSONObject reqJson);
	
	JSONObject deleteSupplier(JSONObject reqJson);
	
	JSONObject updateSupplier(JSONObject reqJson);
	
	JSONObject searchSupplier(JSONObject reqJson);
	
	JSONObject listSuppliers(JSONObject reqJson);
}
