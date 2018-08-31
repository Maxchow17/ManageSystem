package com.max.ordermanage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@PostMapping("/create")
	public JSONObject createProduct(@RequestBody JSONObject reqJson) {
		logger.info("createProduct: " + reqJson);
		return productService.createProduct(reqJson);
	}
	
	@PostMapping("/update")
	public JSONObject updateProduct(@RequestBody JSONObject reqJson) {
		logger.info("updateProduct: " + reqJson);
		return productService.updateProduct(reqJson);
	}
	
	@PostMapping("/delete")
	public JSONObject deleteProduct(@RequestBody JSONObject reqJson) {
		logger.info("deleteProduct: " + reqJson);
		return productService.deleteProduct(reqJson);
	}
	
	@GetMapping("/list")
	public JSONObject listProduct(@RequestParam String currentPage, @RequestParam String pageSize) {
		logger.info("listProduct");
		
		JSONObject reqJson = new JSONObject();
		reqJson.put("currentPage", Integer.valueOf(currentPage));
		reqJson.put("pageSize", Integer.valueOf(pageSize));
		
		return productService.listProducts(reqJson);
	}
}
