package com.max.ordermanage.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger("GlobalExceptionHandle");

	/**
	 * 服务器异常
	 * */
	@ExceptionHandler(value = Exception.class)
	public JSONObject defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		logger.error("服务异常", e);
		return JsonUtil.errJson(ErrorEnum.E_106);
	}
	
	/**
	 * 请求参数缺失
	 * */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public JSONObject missingRequestParameterHandler() throws Exception {
		logger.info("请求参数缺失");
		return JsonUtil.errJson(ErrorEnum.E_107);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public JSONObject messageNotReadableHandler() throws Exception {
		logger.info("请求参数缺失");
		return JsonUtil.errJson(ErrorEnum.E_107);
	}
	
	/**
	 * 无效请求异常
	 * */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public JSONObject httpRequestMethodHandler() throws Exception {
		logger.info("无效请求");
		return JsonUtil.errJson(ErrorEnum.E_104);
	}
	
	/**
	 * 权限不足异常
	 * */
	@ExceptionHandler(UnauthorizedException.class)
	public JSONObject unauthorizedExceptionHandler() throws Exception {
		logger.error("权限异常");
		return JsonUtil.errJson(ErrorEnum.E_103);
	}
	
	/**
	 * 未登录异常
	 * */
	@ExceptionHandler(UnauthenticatedException.class)
	public JSONObject unauthenticationExceptionHandler() throws Exception {
		logger.error("未授权");
		return JsonUtil.errJson(ErrorEnum.E_102);
	}
	
	/**
	 * 文件下载出错
	 * */
	@ExceptionHandler(IOException.class)
	public JSONObject downloadFileExceptionHandler() throws Exception {
		logger.error("下载文件出错");
		return JsonUtil.errJson(ErrorEnum.E_105);
	}
}


