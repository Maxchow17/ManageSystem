package com.max.ordermanage.util;

public enum ErrorEnum {
	
	/**
	 * 错误码和错误信息
	 * */
	// 基础操作
	E_101("101", "未登录"),
	E_102("102", "登录超时"),
	E_103("103", "权限错误"),
	E_104("104", "无效请求地址"),
	E_105("105", "文件下载异常"),
	E_106("106", "服务器异常"),
	E_107("107", "请求参数缺失"),
	E_108("108", "对象不存在"),
	// 客户操作
	E_2000("2000", "客户名称已存在"),
	E_2001("2001", "客户不存在"),
	// 配件操作
	E_3000("3000", "配件名称已存在"),
	E_3001("3001", "配件不存在"),
	// 产品类型操作
	E_4000("4000", "产品类型已存在"),
	E_4001("4001", "产品类型操作出错"),
	// 地理信息操作
	E_5000("5000", "地理信息已存在"),
	E_5001("5001", "地理信息不存在"),
	E_5002("5002", "地理信息不完整"),
	// 询价操作
	E_6000("6000", "询价操作出错"),
	// 订单操作
	E_7000("7000", "订单操作出错"),
	E_7001("7001", "订单不存在"),
	// 供应商操作
	E_8000("8000", "供应商操作出错")
	;
	
	ErrorEnum() {
	}
	
	ErrorEnum(String returnCode, String returnMsg) {
		this.errCode = returnCode;
		this.errMsg = returnMsg;
	}
	
	// 错误码
	private String errCode;
	
	// 错误信息
	private String errMsg;
	
	public String getErrCode() {
		return this.errCode;
	}
	
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	
	public String getErrMsg() {
		return this.errMsg;
	}
	
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}

