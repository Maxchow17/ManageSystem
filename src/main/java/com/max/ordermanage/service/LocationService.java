package com.max.ordermanage.service;

import com.alibaba.fastjson.JSONObject;

public interface LocationService {

	JSONObject createLoction(JSONObject reqJson);
	
	JSONObject updateLocation(JSONObject reqJson);
	
	JSONObject deleteLocation(JSONObject reqJson);
	
	JSONObject listLocations(JSONObject reqJson);
	
	JSONObject listCitiesByCountry(JSONObject reqJson);
	
	JSONObject suggestCitiesByCountry(JSONObject reqJson);
}
