package com.jsp.bankApp.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.jsp.bankApp.dto.Account;


public interface AccountRepo  extends JpaRepository<Account, Integer>{
	
	@Query
	Optional<Account> findByAccountNumber(String accountNumber);
}
