package com.phyopyae.digitalbank.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SettingDto {

	private Date currentDate;
	private Map<String, Object> exchangeRateList;
	private String homeCurrency;
	private List<String> availabeCurrency;

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Map<String, Object> getExchangeRateList() {
		return exchangeRateList;
	}

	public void setExchangeRateList(Map<String, Object> exchangeRateList) {
		this.exchangeRateList = exchangeRateList;
	}

	public String getHomeCurrency() {
		return homeCurrency;
	}

	public void setHomeCurrency(String homeCurrency) {
		this.homeCurrency = homeCurrency;
	}

	public List<String> getAvailabeCurrency() {
		return availabeCurrency;
	}

	public void setAvailabeCurrency(List<String> availabeCurrency) {
		this.availabeCurrency = availabeCurrency;
	}
	
}
