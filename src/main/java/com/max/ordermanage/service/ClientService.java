package com.max.ordermanage.service;

import com.alibaba.fastjson.JSONObject;

public interface ClientService {

	JSONObject createClient(JSONObject reqJson);
	
	JSONObject deleteClient(JSONObject reqJson);
	
	JSONObject updateClient(JSONObject reqJson);
	
	JSONObject searchClient(JSONObject reqJson);
	
	JSONObject listClients(JSONObject reqJson);
}
