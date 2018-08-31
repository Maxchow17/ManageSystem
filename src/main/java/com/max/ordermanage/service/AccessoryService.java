package com.max.ordermanage.service;

import com.alibaba.fastjson.JSONObject;

public interface AccessoryService {

	JSONObject createAccessory(JSONObject reqJson);
	
	JSONObject deleteAccessory(JSONObject reqJson);
	
	JSONObject updateAccessory(JSONObject reqJson);
	
	JSONObject searchAccessory(JSONObject reqJson);
	
	JSONObject listAccessories(JSONObject reqJson);
}
