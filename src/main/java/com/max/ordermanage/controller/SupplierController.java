package com.max.ordermanage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	private Logger logger = LoggerFactory.getLogger(SupplierController.class);
	
	@PostMapping("/create")
	public JSONObject createSupplier(@RequestBody JSONObject reqJson) {
		logger.info("createSupplier: " + reqJson);
		return supplierService.createSupplier(reqJson);
	}
	
	@PostMapping("/update")
	public JSONObject updateSupplier(@RequestBody JSONObject reqJson) {
		logger.info("updateSupplier: " + reqJson);
		return supplierService.updateSupplier(reqJson);
	}
	
	@DeleteMapping("/delete")
	public JSONObject deleteSupplier(@RequestBody JSONObject reqJson) {
		logger.info("deleteSupplier: " + reqJson);
		return supplierService.deleteSupplier(reqJson);
	}
}
