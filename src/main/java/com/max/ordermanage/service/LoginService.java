package com.max.ordermanage.service;

import com.alibaba.fastjson.JSONObject;

public interface LoginService {

	JSONObject auth(JSONObject reqJson);
	
	JSONObject logout();
}
