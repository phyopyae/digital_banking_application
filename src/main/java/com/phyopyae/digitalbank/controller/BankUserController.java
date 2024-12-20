package com.phyopyae.digitalbank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phyopyae.digitalbank.entity.BankUser;
import com.phyopyae.digitalbank.service.BankUserService;

@RestController
@RequestMapping("/api/users")
public class BankUserController {

	private final BankUserService userService;

	public BankUserController(BankUserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<BankUser> createBankUser(@RequestBody BankUser user) {
		if (userService.getBankUserByUserName(user.getUserName()).isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		
		BankUser createdUser = userService.saveUser(user);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteBankUser(@PathVariable Long userId) {
		if (userService.getBankUserById(userId).isPresent()) {
			userService.deleteBankUser(userId);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<BankUser> getBankUserById(@PathVariable Long userId) {
		Optional<BankUser> existingBankUser = userService.getBankUserById(userId);
		if (existingBankUser.isPresent()) {
			return ResponseEntity.ok(existingBankUser.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping
	ResponseEntity<Page<BankUser>> getSearchBankUser(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "userId") String orderBy,
			@RequestParam(defaultValue = "ASC") String direction,
			@RequestParam(defaultValue = "All") String userName) {
		return ResponseEntity.ok().body(userService.getSearchBankUser(page, size, orderBy, direction, userName));
	}
	
	@GetMapping("/open_account")
	public List<BankUser> getAllBankUserWithOpenAccount() {
		return userService.searchAllUserWithOpenAccount();
	}
}
