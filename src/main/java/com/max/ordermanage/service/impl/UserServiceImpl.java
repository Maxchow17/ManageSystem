package com.max.ordermanage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.dao.UserDao;
import com.max.ordermanage.domain.User;
import com.max.ordermanage.service.UserService;
import com.max.ordermanage.util.JsonUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public JSONObject createUser(JSONObject reqJson) {
		User user = new User();
		user.setName(reqJson.getString("username"));
		user.setPassword(reqJson.getString("password"));
		userDao.save(user);
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("result", "success");
		return JsonUtil.successJson(resultObj);
	}

	@Override
	@Transactional
	public JSONObject deleteUser(JSONObject reqJson) {
		String id = reqJson.getString("id");
		try {
			userDao.deleteById(Long.valueOf(id));
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		return JsonUtil.operationResult(true);
	}

	@Override
	@Transactional
	public JSONObject updateUser(JSONObject reqJson) {
		String id = reqJson.getString("id");
		String name = reqJson.getString("username");
		String password = reqJson.getString("password");
		
		User user = userDao.getOne(Long.valueOf(id));
		if (null == user) {
			return JsonUtil.operationResult(false);
		} 
		
		user.setName(name);
		user.setPassword(password);
		
		try {
			userDao.save(user);	
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	@Override
	@Transactional
	public JSONObject findUser(JSONObject reqJson) {
		String name = reqJson.getString("username");
		List<User> users = userDao.findByName(name);
		JSONObject resultJson = new JSONObject();
		resultJson.put("list", users);
		return JsonUtil.successJson(resultJson);
	}

	@Override
	@Transactional
	public User findUserByNameAndPassword(String username, String password) {
		return userDao.findByNameAndPassword(username, password);
	}

}
