package com.max.ordermanage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.util.Date;

// 订单
@Entity
public class SalesOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 订单名称（通过日期产生）
	private String name;
	
	// 订单客户
	@ManyToOne
	private Client client;
	
	// 报价
	@OneToOne
	private Quotation quotation;
	
	// 开始制造日期
	private Date startDate;
	
	// 预计交付日期
	private Date endDate;
	
	// 实际交付日期
	private Date actualEndDate;
	
	// 当日汇率
	private Double exchangeRate;
	
	// 订单评分
	private Double rate;
	
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public Quotation getQuotation() {
		return quotation;
	}

	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}
	

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "SalesOrder [id=" + id + ", name=" + name + ", client=" + client + ", quotation=" + quotation
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", actualEndDate=" + actualEndDate
				+ ", exchangeRate=" + exchangeRate + ", rate=" + rate + ", note=" + note + "]";
	}

}
