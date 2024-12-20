package com.phyopyae.digitalbank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.phyopyae.digitalbank.entity.BankUser;
import com.phyopyae.digitalbank.enums.BankUserStatus;
import com.phyopyae.digitalbank.enums.BankUserType;

@SpringBootTest
@Transactional
public class BankUserServiceTest {

    @Autowired
    private BankUserService bankUserService;

    private BankUser bankUser;

    @BeforeEach
    public void setUp() {
        bankUser = new BankUser();
        bankUser.setUserName("JUnit_tester_1");
        bankUser.setEmail("junittester@test.com");
        bankUser.setPhoneNumber("12345678");
        bankUser.setStatus(BankUserStatus.ACTIVE);
        bankUser.setType(BankUserType.USER);
    }

    @Test
    public void testSaveUser() {
    	BankUser savedBankUser = bankUserService.saveUser(bankUser);
    	
        assertEquals(bankUser.getUserName(), savedBankUser.getUserName());
        assertEquals(bankUser.getEmail(), savedBankUser.getEmail());
        assertEquals(bankUser.getPhoneNumber(), savedBankUser.getPhoneNumber());
        assertEquals(bankUser.getStatus(), savedBankUser.getStatus());
        assertEquals(bankUser.getType(), savedBankUser.getType());
    }

    @Test
    public void testGetBankUserByUserName() {
    	bankUserService.saveUser(bankUser);
    	Optional<BankUser> searchedBankUser = bankUserService.getBankUserByUserName(bankUser.getUserName());
    	
    	assertTrue(searchedBankUser.isPresent());
    }
    
    @Test
    public void testGetBankUserById() {
    	BankUser savedBankUser = bankUserService.saveUser(bankUser);
    	Optional<BankUser> searchedBankUser = bankUserService.getBankUserById(savedBankUser.getUserId());
    	
    	assertTrue(searchedBankUser.isPresent());
    }
    
    @Test
    public void testGetSearchBankUserWithUserName() {
    	Integer page = 0;
    	Integer size = 5;
    	String orderBy = "userId";
    	String direction = "DESC";
    	String userName = bankUser.getUserName();
    	int expectedSize = 1;
    	
    	bankUserService.saveUser(bankUser);
    	
    	Page<BankUser> searchedBankUserPageResult = bankUserService.getSearchBankUser(page, size, orderBy, direction, userName);
    	
    	List<BankUser> searchedBankUserList = searchedBankUserPageResult.getContent();
    	
    	assertEquals(expectedSize, searchedBankUserList.size());
    	
    	for (BankUser searchedBankUser : searchedBankUserList) {
            assertEquals(bankUser.getUserName(), searchedBankUser.getUserName());
            assertEquals(bankUser.getEmail(), searchedBankUser.getEmail());
            assertEquals(bankUser.getPhoneNumber(), searchedBankUser.getPhoneNumber());
            assertEquals(bankUser.getStatus(), searchedBankUser.getStatus());
            assertEquals(bankUser.getType(), searchedBankUser.getType());
    	}
    }
    
    @Test
    public void testGetSearchBankUserWithoutUserName() {
    	Integer page = 0;
    	Integer size = 5;
    	String orderBy = "userId";
    	String direction = "DESC";
    	int expectedSize = 5;
    	
    	bankUserService.saveUser(bankUser);
    	
    	Page<BankUser> searchedBankUserPageResult = bankUserService.getSearchBankUser(page, size, orderBy, direction, null);
    	
    	List<BankUser> searchedBankUserList = searchedBankUserPageResult.getContent();
    	
    	assertEquals(expectedSize, searchedBankUserList.size());
    }
	
    @Test
    public void testDeleteBankUser() {
    	BankUser savedBankUser = bankUserService.saveUser(bankUser);
    	
        assertEquals(bankUser.getUserName(), savedBankUser.getUserName());
        assertEquals(bankUser.getEmail(), savedBankUser.getEmail());
        assertEquals(bankUser.getPhoneNumber(), savedBankUser.getPhoneNumber());
        assertEquals(bankUser.getStatus(), savedBankUser.getStatus());
        assertEquals(bankUser.getType(), savedBankUser.getType());
        
    	bankUserService.deleteBankUser(savedBankUser.getUserId());
    	
    	Optional<BankUser> searchedBankUser = bankUserService.getBankUserById(savedBankUser.getUserId());
    	
    	assertTrue(searchedBankUser.isEmpty());
    }
}
