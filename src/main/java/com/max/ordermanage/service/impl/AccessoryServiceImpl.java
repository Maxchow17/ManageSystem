package com.max.ordermanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.max.ordermanage.dao.AccessoryDao;
import com.max.ordermanage.domain.Accessory;
import com.max.ordermanage.service.AccessoryService;
import com.max.ordermanage.util.ErrorEnum;
import com.max.ordermanage.util.JsonUtil;
import com.max.ordermanage.util.Tools;

@Service
public class AccessoryServiceImpl implements AccessoryService {

	@Autowired
	private AccessoryDao accessoryDao;
	
	// 创建配件
	@Override
	public JSONObject createAccessory(JSONObject reqJson) {
		String name = reqJson.getString("name");
		String specification = reqJson.getString("specification");
		String note = reqJson.getString("note");
		
		try {
			Accessory accessory = new Accessory();
			accessory.setName(name);
			accessory.setSpecification(specification);
			accessory.setNote(note);
			accessoryDao.save(accessory);
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		return JsonUtil.operationResult(true);
	}

	// 删除配件
	@Override
	public JSONObject deleteAccessory(JSONObject reqJson) {
		String id = reqJson.getString("id");

		try {
			accessoryDao.deleteById(Long.valueOf(id));
		} catch (Exception e) {
			return JsonUtil.operationResult(false);
		}
		return JsonUtil.operationResult(true);
	}

	// 更新配件信息
	@Override
	public JSONObject updateAccessory(JSONObject reqJson) {
		String id = reqJson.getString("id");
		String name = reqJson.getString("name");
		String specification = reqJson.getString("specification");
		String note = reqJson.getString("note");
		
		Accessory accessoryToUpdate = accessoryDao.getOne(Long.valueOf(id));
		if (null == accessoryToUpdate) {
			return JsonUtil.errJson(ErrorEnum.E_3000);
		}
		
		accessoryToUpdate.setName(name);
		accessoryToUpdate.setSpecification(specification);
		accessoryToUpdate.setNote(note);
		accessoryDao.save(accessoryToUpdate);
		
		return JsonUtil.operationResult(true);
	}

	// 查找配件
	@Override
	public JSONObject searchAccessory(JSONObject reqJson) {
		String option = reqJson.getString("option");
		String keyword = reqJson.getString("keyword");
		
		List<Accessory> resultList= new ArrayList<>();
		JSONObject resultJson = new JSONObject();
		
		if (option.equals("name")) {
			resultList = accessoryDao.findByNameLike(Tools.fuzzyKeyword(keyword));
		} else if (option.equals("specification")) {
			resultList = accessoryDao.findBySpecificationLike(Tools.fuzzyKeyword(keyword));
		} else if (option.equals("note")) {
			resultList = accessoryDao.findByNoteLike(Tools.fuzzyKeyword(keyword));
		}
		
		resultJson.put("list", resultList);
		return JsonUtil.successJson(resultJson);
	}

	// 分页罗列配件
	@Override
	public JSONObject listAccessories(JSONObject reqJson) {
		int currentPage = reqJson.getInteger("currentPage");
		int pageSize = reqJson.getInteger("pageSize");
		
		JSONObject resultJson = new JSONObject();
		Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.Direction.DESC, "id");
		Page<Accessory> accessoryPage = accessoryDao.findAll(pageable);
		resultJson.put("list", accessoryPage.getContent());
		resultJson.put("totalCount", accessoryPage.getTotalElements());
		
		return JsonUtil.successJson(resultJson);
	}
}
