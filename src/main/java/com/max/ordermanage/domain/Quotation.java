package com.max.ordermanage.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// 询价
@Entity
public class Quotation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
	// 产品
	@ManyToOne(fetch = FetchType.EAGER)
	private Product product;
	
	// 客户
	@ManyToOne(fetch = FetchType.EAGER)
	private Client client;
	
	// 供应商
	@ManyToOne(fetch = FetchType.EAGER)
	private Supplier supplier;
	
	// 采购单价
	private Double purchasePrice;
	
	// 销售单价
	private Double salesPrice;
	
	// 数量
	private Integer number;
	
	// 报价日期
	private Date date;
	
	// 备注
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "Quotation [id=" + id + ", name=" + name + ", product=" + product + ", client=" + client + ", supplier="
				+ supplier + ", purchasePrice=" + purchasePrice + ", salesPrice=" + salesPrice + ", number=" + number
				+ ", date=" + date + ", note=" + note + "]";
	}
	
}