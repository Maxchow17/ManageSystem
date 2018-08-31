package com.max.ordermanage.util;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
	
	// compose success json
	public static JSONObject successJson(Object data) {
		JSONObject returnJson = new JSONObject();
		returnJson.put(Constants.CODE_RETURN, Constants.SUCCESS_CODE);
		returnJson.put(Constants.MSG_RETURN, Constants.SUCCESS_MSG);
		returnJson.put(Constants.DATA_RETURN, data);
		return returnJson;
	}
	
	// compose error json
	public static JSONObject errJson(ErrorEnum errEnum) {
		JSONObject returnJson = new JSONObject();
		returnJson.put(Constants.CODE_RETURN, errEnum.getErrCode());
		returnJson.put(Constants.MSG_RETURN, errEnum.getErrMsg());
		return returnJson;
	}
	
	// operation success
	public static JSONObject operationResult(boolean isSuccess) {
		JSONObject resultObj = new JSONObject();
		if (isSuccess) {
			resultObj.put("result", "success");	
		} else {
			resultObj.put("result", "fail");
		}
		return successJson(resultObj);
	}
}
