package com.phyopyae.digitalbank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phyopyae.digitalbank.entity.BankAccount;
import com.phyopyae.digitalbank.repository.BankAccountRepository;

@Service
public class BankAccountService {

	private final BankAccountRepository accountRepository;

	public BankAccountService(BankAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public List<BankAccount> getAllBankAccounts() {
		return accountRepository.findAll();
	}
	
	public Optional<BankAccount> getBankAccountById(Long accountId) {
		return accountRepository.findById(accountId);
	}

	@Transactional
	public BankAccount saveBankAccount(BankAccount account) {
		return accountRepository.save(account);
	}

	public BankAccount updateBankAccount(Long id, BankAccount account) {
		Optional<BankAccount> existingBankAccount = accountRepository.findById(id);
		if (existingBankAccount.isPresent()) {
			BankAccount bankAccount = existingBankAccount.get();
			bankAccount.setAccountNumber(account.getAccountNumber());
			bankAccount.setAccountBalance(account.getAccountBalance());
			bankAccount.setCurrency(account.getCurrency());
			bankAccount.setAccountStatus(account.getAccountStatus());
			accountRepository.save(bankAccount);
			return bankAccount;
		}
		return null;

	}
}
