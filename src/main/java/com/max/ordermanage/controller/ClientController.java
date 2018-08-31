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
import com.max.ordermanage.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	private Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@PostMapping("/create")
	public JSONObject createClient(@RequestBody JSONObject reqJson) {
		logger.info("createClient: " + reqJson);
		return clientService.createClient(reqJson);
	}
	
	@PostMapping("/update")
	public JSONObject updateClient(@RequestBody JSONObject reqJson) {
		logger.info("updateClient: " + reqJson);
		return clientService.updateClient(reqJson);
	}
	
	@DeleteMapping("/delete")
	public JSONObject deleteClient(@RequestBody JSONObject reqJson) {
		logger.info("deleteClient: " + reqJson);
		return clientService.deleteClient(reqJson);
	}
	
	@GetMapping("/list")
	public JSONObject listClient(@RequestParam String currentPage, @RequestParam String pageSize) {
		logger.info("listClient");
		
		int nCurrentPage = Integer.valueOf(currentPage);
		int nPageSize = Integer.valueOf(pageSize);
		
		JSONObject reqJson = new JSONObject();
		reqJson.put("currentPage", nCurrentPage);
		reqJson.put("pageSize", nPageSize);
		return clientService.listClients(reqJson);
	}
}
