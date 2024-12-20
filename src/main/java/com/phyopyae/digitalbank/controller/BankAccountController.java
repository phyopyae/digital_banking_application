package com.phyopyae.digitalbank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phyopyae.digitalbank.entity.BankAccount;
import com.phyopyae.digitalbank.service.BankAccountService;

@RestController
@RequestMapping("/api/account")
public class BankAccountController {

	private final BankAccountService accountService;

	public BankAccountController(BankAccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping
	public List<BankAccount> getAllBankAccounts() {
		return accountService.getAllBankAccounts();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BankAccount> getBankAccountById(@PathVariable Long id) {
		Optional<BankAccount> existingBankAccount = accountService.getBankAccountById(id);
		if (existingBankAccount.isPresent()) {
			return ResponseEntity.ok(existingBankAccount.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount account) {
		BankAccount createdAccount = accountService.saveBankAccount(account);
		return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}")
    public ResponseEntity<BankAccount> updateBankAccount(@PathVariable Long id, @RequestBody BankAccount account) {
		BankAccount updatedAccount = accountService.updateBankAccount(id, account);
		if (null == updatedAccount) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updatedAccount);
	}
}
