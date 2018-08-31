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
import com.max.ordermanage.service.SalesOrderService;

@RestController
@RequestMapping("/salesOrder")
public class SalesOrderController {

	@Autowired
	private SalesOrderService salesOrderService;
	
	private Logger logger = LoggerFactory.getLogger(SalesOrderController.class);
	
	@PostMapping("/create")
	public JSONObject createSalesOrder(@RequestBody JSONObject reqJson) {
		logger.info("createSalesOrder: " + reqJson);
		return salesOrderService.createSalesOrder(reqJson);
	}
	
	@PostMapping("/update")
	public JSONObject updateSalesOrder(@RequestBody JSONObject reqJson) {
		logger.info("updateSalesOrder: " + reqJson);
		return salesOrderService.updateSalesOrder(reqJson);
	}
	
	@DeleteMapping("/delete")
	public JSONObject deleteSalesOrder(@RequestBody JSONObject reqJson) {
		logger.info("deleteSalesOrder: " + reqJson);
		return salesOrderService.deleteSalesOrder(reqJson);
	}
	
	@GetMapping("/list")
	public JSONObject listSalesOrder(@RequestParam String currentPage, @RequestParam String pageSize) {
		logger.info("listSalesOrder");
		JSONObject reqJson = new JSONObject();
		reqJson.put("currentPage", Integer.valueOf(currentPage));
		reqJson.put("pageSize", Integer.valueOf(pageSize));
		return salesOrderService.listSalesOrders(reqJson);
	}
}
