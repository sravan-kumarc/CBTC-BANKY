package com.jsp.bankApp.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.bankApp.dto.Account;
import com.jsp.bankApp.dto.Transaction;



public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByAccount(Account account);
}
