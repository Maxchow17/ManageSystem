package com.max.ordermanage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.max.ordermanage.dao.ClientDao;
import com.max.ordermanage.dao.ProductDao;
import com.max.ordermanage.dao.QuotationDao;
import com.max.ordermanage.dao.SalesOrderDao;
import com.max.ordermanage.dao.UserDao;
import com.max.ordermanage.domain.Client;
import com.max.ordermanage.domain.Quotation;
import com.max.ordermanage.domain.SalesOrder;
import com.max.ordermanage.domain.User;
import com.max.ordermanage.service.AccessoryService;
import com.max.ordermanage.service.CategoryService;
import com.max.ordermanage.service.ClientService;
import com.max.ordermanage.service.LocationService;
import com.max.ordermanage.service.LoginService;
import com.max.ordermanage.service.ProductService;
import com.max.ordermanage.service.QuotationService;
import com.max.ordermanage.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagementSystemApplicationTests {

	@Autowired
	ClientDao clientDao;
	
	@Autowired
	SalesOrderDao salesOrderDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	AccessoryService accessoryService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	QuotationService quotationService;
	
	@Autowired
	LocationService locationService;
	
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void findSalesOrderById() {
		Optional<SalesOrder> orderOptional = salesOrderDao.findById(Long.valueOf(2));
		SalesOrder order = orderOptional.get();
		System.out.println(order.toString());
	}
	
	@Test
	public void salesOrder(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date startDate = dateFormat.parse("2001-01-01");
			Date endDate = dateFormat.parse("2018-08-20");
			
			List<SalesOrder> orderList = salesOrderDao.findByStartDateBetween(startDate, endDate);
			if (orderList.isEmpty()) {
				System.out.println("Empty list");
			} else {
				for(SalesOrder order : orderList) {
					System.out.println(order);
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void userTest() {
		User user = userDao.findByNameAndPassword("admin", "123");
		if (null == user) {
			System.out.println("No Matching User");
		} else {
			System.out.println(user);
		}
	}
	
	@Test
	public void createUserTest() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("username", "admin");
		reqJson.put("password", "123");
		System.out.println(userService.createUser(reqJson));
	}
	
	@Test
	public void getSalesOrderTest() {
		Client client = clientDao.findByName("麦当劳");
		List<SalesOrder> list = salesOrderDao.findByClient(client);
		if (!list.isEmpty()) {
			for (SalesOrder order : list) {
				System.out.println(order);
			}
		}
	}
	
	// location test
	@Test
	public void createLocation() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("country", "country1");
		reqJson.put("city", "city0");
		for (int i = 1; i < 10; i++) {
			reqJson.replace("city", "city1" + i);
			locationService.createLoction(reqJson);
		}
	}
	
	@Test
	public void deleteLocation() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("country", "testCountry");
		reqJson.put("city", "testCity");
		System.out.println(locationService.deleteLocation(reqJson));
	}
	
	@Test
	public void searchLocation() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("country", "country1");
		reqJson.put("currentPage", 1);
		reqJson.put("pageSize", 5);
		System.out.println(locationService.listCitiesByCountry(reqJson));
	}
	
	@Test
	public void suggestCities() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("country", "country1");
		reqJson.put("city", "city1");
		System.out.println(locationService.suggestCitiesByCountry(reqJson));
	}
	
	// client test
	@Test
	public void createClientTest() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("name", "testCompany");
		reqJson.put("phone", "012345768");
		reqJson.put("city", "testCity");
		reqJson.put("country", "testCountry");
		clientService.createClient(reqJson);
	}
	
	@Test
	public void deleteClientTest() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("name", "testCompany");
		clientService.deleteClient(reqJson);
	}
	
	@Test
	public void searchClientTest() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("option", "name");
		reqJson.put("keyword", "%集团%");
		System.out.println(clientService.searchClient(reqJson));
	}
	
	// accessory test
	@Test
	public void createAccessory() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("name", "accessory_1");
		reqJson.put("specification", "specification_1");
		reqJson.put("note", "note_1");
		for (int i = 1; i < 100; i++)
		{
			reqJson.replace("name", "accessory_" + i);
			reqJson.replace("specification", "specification_" + i);
			reqJson.replace("note", "note_" + i);
			System.out.println(accessoryService.createAccessory(reqJson));
		}
	}
	
	@Test
	public void deleteAccessory() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("id", "");
		for (int i = 100; i < 100 + 100; i++) {
			reqJson.replace("id", String.valueOf(i));
			System.out.println(accessoryService.deleteAccessory(reqJson));
		}
	}
	
	@Test
	public void seachAccessory() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("option", "name");
		reqJson.put("keyword", "_1");
		System.out.println("by Name");
		System.out.println(accessoryService.searchAccessory(reqJson));
		
		reqJson.replace("option", "specification");
		reqJson.replace("keyword", "_2");
		System.out.println("by Specification");
		System.out.println(accessoryService.searchAccessory(reqJson));
		
		reqJson.replace("option", "note");
		reqJson.replace("keyword", "_3");
		System.out.println("by Note");
		System.out.println(accessoryService.searchAccessory(reqJson));
	}
	
	// category test
	@Test
	public void createCategory() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("name", "category_1");
		reqJson.put("note", "category_note_1");
		System.out.println(categoryService.createCategory(reqJson));
		
		reqJson.put("name", "category_2");
		reqJson.put("note", "category_note_2");
		System.out.println(categoryService.createCategory(reqJson));
		
		reqJson.put("name", "category_3");
		reqJson.put("note", "category_note_3");
		System.out.println(categoryService.createCategory(reqJson));
	}
	
	@Test
	public void deleteCategory() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("id", "1");
		System.out.println(categoryService.deleteCategory(reqJson));
	}
	
	// product test
	@Test
	public void createProduct() {
		JSONObject reqJson = new JSONObject();
		
		reqJson.put("name", "product_2");
		reqJson.put("categoryId", "2");
		
		List<String> accessoryIds = new ArrayList<>();
		accessoryIds.add("22");
		accessoryIds.add("33");
		accessoryIds.add("44");
		accessoryIds.add("55");
		accessoryIds.add("66");
		reqJson.put("accessoryIds", accessoryIds);
		
		String imgUrl = "imgUrl1";
		reqJson.put("imgUrl", imgUrl);
		
		String specification = "specification prodcut 2";
		reqJson.put("specification", specification);
		
		String note = "note product 2";
		reqJson.put("note", note);
		
		System.out.println(productService.createProduct(reqJson));
	}
	
	@Test
	public void deleteProduct() {
		JSONObject reqJson = new JSONObject();
		
		reqJson.put("id", "1");
		System.out.println(productService.deleteProduct(reqJson));
	}
	
	@Test
	public void searchProduct() {
		JSONObject reqJson = new JSONObject();
//		reqJson.put("option", "name");
//		reqJson.put("keyword", "_1");
//		System.out.println(productService.searchProduct(reqJson));
//		
//		reqJson.put("option", "accessoryId");
//		reqJson.put("keyword", "22");
//		System.out.println(productService.searchProduct(reqJson));
//		
		reqJson.put("option", "accessoryId");
		reqJson.put("keyword", "44");
		System.out.println(productService.searchProduct(reqJson));
	}
	
	@Test
	public void createQuotation() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("name", "quation_1");
		reqJson.put("clientId", "1");
		reqJson.put("productId", "2");
		reqJson.put("supplierId", "1");
		reqJson.put("salesPrice", "100");
		reqJson.put("purchasePrice", "50");
		reqJson.put("number", "100");
		reqJson.put("note", "note for quotation 1");
		
		System.out.println(quotationService.createQuotation(reqJson));
	}
	
	@Test
	public void deleteQuotation() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("id", "2");
		System.out.println(quotationService.deleteQuotation(reqJson));
	}
	
	@Test
	public void updateQuotationSalesPrice() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("id", "2");
		reqJson.put("salesPrice", 250.0);
		System.out.println(quotationService.updateSalesPrice(reqJson));
	}
	
	@Test
	public void updateQuotationPurchasePrice() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("id", "2");
		reqJson.put("purchasePrice", 90.0);
		System.out.println(quotationService.updatePurchasePrice(reqJson));
	}
	
	@Test
	public void searchQuotation() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("option", "name");
		reqJson.put("keyword", "qu");
		System.out.println(quotationService.searchQuotation(reqJson));
	}
	
	@Test
	public void findAllQuotation() {
		
	}
	
	// category
	@Test
	public void listCategory() {
		JSONObject reqJson = new JSONObject();
		reqJson.put("currentPage", 1);
		reqJson.put("pageSize", 2);
		System.out.println(categoryService.listCategories(reqJson));
	}
}
