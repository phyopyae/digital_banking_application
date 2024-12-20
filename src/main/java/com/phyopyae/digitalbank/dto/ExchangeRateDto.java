package com.phyopyae.digitalbank.dto;

import java.math.BigDecimal;

public class ExchangeRateDto {

	private String name;
	private BigDecimal rate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}
