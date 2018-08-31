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
import com.max.ordermanage.service.AccessoryService;

@RestController
@RequestMapping("/accessory")
public class AccessoryController {

	@Autowired
	private AccessoryService accessoryService;
	
	private Logger logger = LoggerFactory.getLogger(AccessoryController.class);
	
	@PostMapping("/create")
	public JSONObject createAccessory(@RequestBody JSONObject reqJson) {
		logger.info("createAccessory: " + reqJson);
		return accessoryService.createAccessory(reqJson);
	}
	
	@PostMapping("/update")
	public JSONObject updateAccessory(@RequestBody JSONObject reqJson) {
		logger.info("updateAccessory" + reqJson);
		return accessoryService.updateAccessory(reqJson);
	}
	
	@DeleteMapping("/delete")
	public JSONObject deleteAccessory(@RequestBody JSONObject reqJson) {
		logger.info("deleteAccessory: " + reqJson);
		return accessoryService.deleteAccessory(reqJson);
	}
	
	@GetMapping("/list")
	public JSONObject listAccessory(@RequestParam int currentPage, @RequestParam int pageSize) {
		logger.info("listAccessory");
		
		JSONObject reqJson = new JSONObject();
		reqJson.put("currentPage", Integer.valueOf(currentPage));
		reqJson.put("pageSize", Integer.valueOf(pageSize));
		return accessoryService.listAccessories(reqJson);
	}
}
