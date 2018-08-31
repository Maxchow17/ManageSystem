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
import com.max.ordermanage.service.QuotationService;

@RestController
@RequestMapping("/quotation")
public class QuotationController {

	@Autowired
	private QuotationService quotationService;
	
	private Logger logger = LoggerFactory.getLogger(QuotationController.class);
	
	@PostMapping("/create")
	public JSONObject createQuotation(@RequestBody JSONObject reqJson) {
		logger.info("createQuotation: " + reqJson);
		return quotationService.createQuotation(reqJson);
	}
	
	@PostMapping("/update")
	public JSONObject udateQuotation(@RequestBody JSONObject reqJson) {
		logger.info("updateQuotation: " + reqJson);
		return quotationService.updateQuotation(reqJson);
	}
	
	@PostMapping("/update/purchasePrice")
	public JSONObject updatePurchasePrice(@RequestBody JSONObject reqJson) {
		logger.info("updatePurchasePrice: " + reqJson);
		return quotationService.updatePurchasePrice(reqJson);
	}
	
	@DeleteMapping("/delete")
	public JSONObject deleteQuotation(@RequestBody JSONObject reqJson) {
		logger.info("deleteQuotation: " + reqJson);
		return quotationService.deleteQuotation(reqJson);
	}
	
	@GetMapping("/list")
	public JSONObject listQuotation(@RequestParam String currentPage, @RequestParam String pageSize) {
		logger.info("listQuotation");
		JSONObject reqJson = new JSONObject();
		reqJson.put("currentPage", Integer.valueOf(currentPage));
		reqJson.put("pageSize", Integer.valueOf(pageSize));
		return quotationService.listQuotations(reqJson);
	}
}
