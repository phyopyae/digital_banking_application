package com.phyopyae.digitalbank.entity;

import java.util.List;

import com.phyopyae.digitalbank.enums.BankUserStatus;
import com.phyopyae.digitalbank.enums.BankUserType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class BankUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	private String userName;

	private String email;

	private String phoneNumber;

	private BankUserStatus status;

	private BankUserType type;
	
    @OneToMany(mappedBy = "userId")
    private List<BankAccount> accounts;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public BankUserStatus getStatus() {
		return status;
	}

	public void setStatus(BankUserStatus status) {
		this.status = status;
	}

	public BankUserType getType() {
		return type;
	}

	public void setType(BankUserType type) {
		this.type = type;
	}

	public List<BankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<BankAccount> accounts) {
		this.accounts = accounts;
	}
}
