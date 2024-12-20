package com.phyopyae.digitalbank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.phyopyae.digitalbank.entity.BankAccount;
import com.phyopyae.digitalbank.enums.AccountStatus;
import com.phyopyae.digitalbank.enums.AccountType;

@SpringBootTest
@Transactional
public class BankAccountServiceTest {

    @Autowired
    private BankAccountService bankAccountService;

    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
    	bankAccount = new BankAccount();
    	bankAccount.setAccountNumber("JU202112201");
    	bankAccount.setAccountStatus(AccountStatus.OPEN);
    	bankAccount.setAccountType(AccountType.SAVING_ACCOUNT);
    	bankAccount.setCurrency("USD");
    	bankAccount.setUserId(2);
    	bankAccount.setAccountBalance(new BigDecimal("2000"));
    }
    
    
    @Test
    public void testSaveBankAccount() {
    	BankAccount savedBankAccount = bankAccountService.saveBankAccount(bankAccount);
    	
        assertEquals(bankAccount.getAccountNumber(), savedBankAccount.getAccountNumber());
        assertEquals(bankAccount.getUserId(), savedBankAccount.getUserId());
        assertEquals(bankAccount.getAccountStatus(), savedBankAccount.getAccountStatus());
        assertEquals(bankAccount.getAccountType(), savedBankAccount.getAccountType());
        assertEquals(bankAccount.getAccountBalance(), savedBankAccount.getAccountBalance());
        assertEquals(bankAccount.getCurrency(), savedBankAccount.getCurrency());
    }
    
    @Test
    public void testGetBankAccountById() {
    	BankAccount savedBankAccount = bankAccountService.saveBankAccount(bankAccount);
    	
    	BankAccount searchedBankAccount = bankAccountService.getBankAccountById(savedBankAccount.getAccountId()).get();
    	
    	assertEquals(savedBankAccount.getAccountNumber(), searchedBankAccount.getAccountNumber());
    }
    
    @Test
    public void testUpdateBankAccount() {
    	BankAccount savedBankAccount = bankAccountService.saveBankAccount(bankAccount);
    	
    	savedBankAccount.setAccountStatus(AccountStatus.CLOSED);
    	savedBankAccount.setAccountBalance(BigDecimal.ZERO);
    	BankAccount updatedBankAccount = bankAccountService.updateBankAccount(savedBankAccount.getAccountId(), savedBankAccount);
    	BankAccount existingBankAccount = bankAccountService.getBankAccountById(savedBankAccount.getAccountId()).get();
    	
        assertEquals(updatedBankAccount.getAccountNumber(), existingBankAccount.getAccountNumber());
        assertEquals(updatedBankAccount.getUserId(), existingBankAccount.getUserId());
        assertEquals(updatedBankAccount.getAccountStatus(), existingBankAccount.getAccountStatus());
        assertEquals(updatedBankAccount.getAccountType(), existingBankAccount.getAccountType());
        assertEquals(updatedBankAccount.getAccountBalance(), existingBankAccount.getAccountBalance());
        assertEquals(updatedBankAccount.getCurrency(), existingBankAccount.getCurrency());
    }
    
    @Test
    public void testUpdateBankAccount_Not_Exist_BankAccount() {
    	BankAccount savedBankAccount = bankAccountService.saveBankAccount(bankAccount);
    	
    	savedBankAccount.setAccountStatus(AccountStatus.CLOSED);
    	savedBankAccount.setAccountBalance(BigDecimal.ZERO);
    	savedBankAccount.setAccountId(0);
    	
    	assertNull(bankAccountService.updateBankAccount(savedBankAccount.getAccountId(), savedBankAccount));
    }
}
