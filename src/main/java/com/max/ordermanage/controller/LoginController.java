package com.max.ordermanage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.service.LoginService;
import com.max.ordermanage.util.ErrorEnum;
import com.max.ordermanage.util.JsonUtil;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	LoginService loginService;

	@PostMapping("/auth")
	public JSONObject auth(@RequestBody JSONObject reqJson) {
		if (reqJson.containsKey("username") && reqJson.containsKey("password")) {
			return loginService.auth(reqJson);
		} else {
			return JsonUtil.errJson(ErrorEnum.E_107);
		}
	}
	
	@GetMapping("/logout")
	public JSONObject logout() {
		return loginService.logout();
	} 
}
