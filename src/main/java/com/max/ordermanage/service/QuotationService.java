package com.max.ordermanage.service;

import com.alibaba.fastjson.JSONObject;

public interface QuotationService {

	JSONObject createQuotation(JSONObject reqJson);
	
	JSONObject deleteQuotation(JSONObject reqJson);
	
	JSONObject updateQuotation(JSONObject reqJson);
	
	JSONObject updateSalesPrice(JSONObject reqJson);
	
	JSONObject updatePurchasePrice(JSONObject reqJson);
	
	JSONObject searchQuotation(JSONObject reqJson);
	
	JSONObject searchByDateRange(JSONObject reqJson);
	
	JSONObject listQuotations(JSONObject reqJson);
}
