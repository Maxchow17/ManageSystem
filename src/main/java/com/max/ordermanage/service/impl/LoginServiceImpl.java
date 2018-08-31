package com.max.ordermanage.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.service.LoginService;
import com.max.ordermanage.util.ErrorEnum;
import com.max.ordermanage.util.JsonUtil;

@Service
public class LoginServiceImpl implements LoginService{

	@Override
	public JSONObject auth(JSONObject reqJson) {
		String username = reqJson.getString("username");
		String password = reqJson.getString("password");
		
		Subject currentUser = SecurityUtils.getSubject();
		//currentUser.getSession().setTimeout(10 * 1000);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		JSONObject resultJson = new JSONObject();
		try {
			currentUser.login(token);
			resultJson.put("result", "success");
		} catch (AuthenticationException e) {
			resultJson.put("result", "failed");
		}
		
		return JsonUtil.successJson(resultJson);
	}

	@Override
	public JSONObject logout() {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
		} catch (Exception e) {
			System.out.println("falied to logout");
			return JsonUtil.errJson(ErrorEnum.E_101);
		}
		return JsonUtil.successJson("logout");
	}

}
