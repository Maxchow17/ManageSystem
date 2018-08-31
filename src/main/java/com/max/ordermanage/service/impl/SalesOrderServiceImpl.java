package com.max.ordermanage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.dao.ClientDao;
import com.max.ordermanage.dao.QuotationDao;
import com.max.ordermanage.dao.SalesOrderDao;
import com.max.ordermanage.domain.Client;
import com.max.ordermanage.domain.Quotation;
import com.max.ordermanage.domain.SalesOrder;
import com.max.ordermanage.service.SalesOrderService;
import com.max.ordermanage.util.ErrorEnum;
import com.max.ordermanage.util.JsonUtil;
import com.max.ordermanage.util.Tools;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {
	
	@Autowired
	private SalesOrderDao salesOrderDao;
	
	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private QuotationDao quotationDao;

	@Override
	@Transactional
	public JSONObject createSalesOrder(JSONObject reqJson) {
		
		String name = reqJson.getString("name");
		String clientId = reqJson.getString("clientId");
		String startDate = reqJson.getString("startDate");
		String endDate = reqJson.getString("EndDate");
		String quotationId = reqJson.getString("quotationId");
		double exchangeRate = reqJson.getDouble("exchangeRate");
		
		// name
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setName(name);
		
		// client
		Client client = clientDao.getOne(Long.valueOf(clientId));
		if (null == client) {
			return JsonUtil.operationResult(false);
		}
		salesOrder.setClient(client);
		
		// start, end, act
		Date sDate = Tools.stringToDate(startDate);
		Date eDate = Tools.stringToDate(endDate);
		if (eDate.before(sDate)) {
			return JsonUtil.operationResult(false);
		}
		Date acDate = eDate;
		salesOrder.setStartDate(sDate);
		salesOrder.setEndDate(eDate);
		salesOrder.setActualEndDate(acDate);
		
		// quotation
		Quotation quotation = quotationDao.getOne(Long.valueOf(quotationId));
		if (null == quotation) {
			return JsonUtil.operationResult(false);
		}
		salesOrder.setQuotation(quotation);
		
		// exchange rate
		salesOrder.setExchangeRate(exchangeRate);
		
		try {
			salesOrderDao.save(salesOrder);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	@Override
	@Transactional
	public JSONObject deleteSalesOrder(JSONObject reqJson) {
		String id = reqJson.getString("id");
		
		try {
			salesOrderDao.deleteById(Long.valueOf(id));
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	@Override
	@Transactional
	public JSONObject updateSalesOrder(JSONObject reqJson) {
		String id = reqJson.getString("id");
		String name = reqJson.getString("name");
		String clientId = reqJson.getString("clientId");
		String quotationId = reqJson.getString("quotationId");
		String startDate = reqJson.getString("startDate");
		String endDate = reqJson.getString("endDate");
		String actaulEndDate = reqJson.getString("actualEndDate");
		double exchangeRate = reqJson.getDouble("exchangeRate");
		
		SalesOrder salesOrder =salesOrderDao.getOne(Long.valueOf(id));
		if (null == salesOrder) {
			return JsonUtil.operationResult(false);
		}
		
		// name
		salesOrder.setName(name);
		
		// client id
		Client client = clientDao.getOne(Long.valueOf(clientId));
		if (null == client) {
			return JsonUtil.operationResult(false);
		}
		salesOrder.setClient(client);
		
		// quotation
		Quotation quotation = quotationDao.getOne(Long.valueOf(quotationId));
		if (null == quotation) {
			return JsonUtil.operationResult(false);
		}
		salesOrder.setQuotation(quotation);
		
		// start, end, actualEnd
		Date sDate = Tools.stringToDate(startDate);
		Date eDate = Tools.stringToDate(endDate);
		Date acDate = Tools.stringToDate(actaulEndDate);
		salesOrder.setStartDate(sDate);
		salesOrder.setEndDate(eDate);
		salesOrder.setActualEndDate(acDate);
		
		// exchange rate
		salesOrder.setExchangeRate(exchangeRate);
		
		try {
			salesOrderDao.save(salesOrder);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		
		return JsonUtil.operationResult(true);
	}

	@Override
	public JSONObject searchSalesOrder(JSONObject reqJson) {
		String option = reqJson.getString("option");
		String keyword = reqJson.getString("keyword");
		
		List<SalesOrder> resultList = new ArrayList<>();
		
		if (option.equals("name")) {
			salesOrderDao.findByNameLike(Tools.fuzzyKeyword(keyword));
		} else if (option.equals("clientId")) {
			salesOrderDao.findByClientId(Long.valueOf(keyword));
		} else if (option.equals("clientName")) {
			salesOrderDao.findByClientName(keyword);
		} else if (option.equals("note")) {
			salesOrderDao.findByNoteLike(Tools.fuzzyKeyword(keyword));
		}
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("list", resultList);
		return JsonUtil.successJson(resultJson);
	}

	@Override
	public JSONObject listSalesOrders(JSONObject reqJson) {
		int currentPage = reqJson.getInteger("currentPage");
		int pageSize = reqJson.getInteger("pageSize");
		
		if (!Tools.verifyPage(currentPage, pageSize)) {
			return JsonUtil.errJson(ErrorEnum.E_108);
		}
		
		currentPage = Tools.adjustPageNum(currentPage);
		
		Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.Direction.DESC, "startDate");
		Page<SalesOrder> salesOrderPage = salesOrderDao.findAll(pageable);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("list", salesOrderPage.getContent());
		resultJson.put("totalCount", salesOrderPage.getTotalElements());
		
		return JsonUtil.successJson(resultJson);
	}

}
