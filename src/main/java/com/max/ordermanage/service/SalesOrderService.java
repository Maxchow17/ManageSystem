package com.max.ordermanage.service;

import com.alibaba.fastjson.JSONObject;

public interface SalesOrderService {

	JSONObject createSalesOrder(JSONObject reqJson);
	
	JSONObject deleteSalesOrder(JSONObject reqJson);
	
	JSONObject updateSalesOrder(JSONObject reqJson);
	
	JSONObject searchSalesOrder(JSONObject reqJson);
	
	JSONObject listSalesOrders(JSONObject reqJson);
}
