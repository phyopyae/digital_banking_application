package com.phyopyae.digitalbank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phyopyae.digitalbank.dto.SettingDto;
import com.phyopyae.digitalbank.service.SettingService;

@RestController
@RequestMapping("/api/setting")
public class HomeController {

	private final SettingService settingService;

	public HomeController(SettingService settingService) {
		this.settingService = settingService;
	}
	
	@GetMapping
	public ResponseEntity<SettingDto> getAllSettings() {
		return ResponseEntity.ok().body(settingService.getAllSettings());
	}
}
