package com.phyopyae.digitalbank.service;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.phyopyae.digitalbank.dto.SettingDto;

@Service
public class SettingService {

	@Value("${digitalbank.bankaccount.exchangerate.apiurl}")
	private String apiUrl;

	@Value("${digitalbank.bankaccount.exchangerate.accesskey}")
	private String accessKey;

	@Value("${digitalbank.bankaccount.exchangerate.homeCurrency}")
	private String baseCurrency;

	private final RestTemplate restTemplate;

	public SettingService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getCurrencyExchangeRate() {
		Map<String, Object> rates = new HashMap<String, Object>();

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_key", accessKey);

		URI uri = URI.create(apiUrl);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uri);
		params.forEach(builder::queryParam);

		String url = builder.toUriString();

		try {
			Map<String, Map<String, Object>> result = restTemplate.getForObject(url, Map.class);
			rates = result.get("rates");
		} catch (Exception exception) {
			exception.getStackTrace();
		}

		return rates;
	}

	public SettingDto getAllSettings() {
		SettingDto setting = new SettingDto();
		setting.setExchangeRateList(getCurrencyExchangeRate());
		setting.setCurrentDate(new Date());
		setting.setHomeCurrency(baseCurrency);
		return setting;
	}
}
