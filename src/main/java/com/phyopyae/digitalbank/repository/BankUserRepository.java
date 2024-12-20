package com.phyopyae.digitalbank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phyopyae.digitalbank.entity.BankUser;
import com.phyopyae.digitalbank.enums.AccountStatus;

public interface BankUserRepository extends JpaRepository<BankUser, Long> {

	@Query("SELECT u FROM BankUser u WHERE u.userName like (:userName)")
	Page<BankUser> search(@Param("userName") String userName, Pageable pageable);

	Optional<BankUser> findByUserName(String userName);
	
	@Query("SELECT u FROM BankUser u JOIN BankAccount a ON u.userId = a.userId WHERE a.accountStatus = (:accountStatus)")
	List<BankUser> searchAllUserWithOpenAccount(@Param("accountStatus") AccountStatus accountStatus);
}
