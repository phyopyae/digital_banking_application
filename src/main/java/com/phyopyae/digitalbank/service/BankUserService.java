package com.phyopyae.digitalbank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phyopyae.digitalbank.entity.BankUser;
import com.phyopyae.digitalbank.enums.AccountStatus;
import com.phyopyae.digitalbank.repository.BankUserRepository;

@Service
public class BankUserService {

	private final BankUserRepository userRepository;

	public BankUserService(BankUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional
	public BankUser saveUser(BankUser user) {
		return userRepository.save(user);
	}
	
	public Optional<BankUser> getBankUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public Optional<BankUser> getBankUserById(Long userId) {
		return userRepository.findById(userId);
	}

	public void deleteBankUser(Long userId) {
		userRepository.deleteById(userId);
	}
	
	public List<BankUser> searchAllUserWithOpenAccount() {
		return userRepository.searchAllUserWithOpenAccount(AccountStatus.OPEN);
	}

	public Page<BankUser> getSearchBankUser(Integer page, Integer size, String orderBy, String direction, String userName) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
		
		if (null != userName && !"All".equalsIgnoreCase(userName)) {
			Page<BankUser> bankUsers = userRepository.search("%"+userName+"%", pageRequest);
			return bankUsers;
		}
		
		Page<BankUser> bankUsers = userRepository.findAll(pageRequest);
		return bankUsers;
	}
}
