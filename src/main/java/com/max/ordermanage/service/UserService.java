package com.max.ordermanage.service;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.domain.User;

public interface UserService {

	JSONObject createUser(JSONObject reqJson);
	
	JSONObject deleteUser(JSONObject reqJson);
	
	JSONObject updateUser(JSONObject reqJson);
	
	JSONObject findUser(JSONObject reqJson);
	
	User findUserByNameAndPassword(String username, String password);
}
