package com.phyopyae.digitalbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phyopyae.digitalbank.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
