package com.jsp.bankApp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.bankApp.Repo.AccountRepo;
import com.jsp.bankApp.Repo.TransactionRepo;
import com.jsp.bankApp.dto.Account;
import com.jsp.bankApp.dto.Transaction;
import com.jsp.bankApp.helper.ResponseStructure;



@RestController
@RequestMapping("/api/bank")
public class TransactionController {
	
	@Autowired
	TransactionRepo transactionRepo;
	
	@Autowired
	AccountRepo accountRepo;
	
	//http://localhost:8080/api/bank/saveTransaction?accountid=accountid
	@PostMapping("saveTransaction")
	public ResponseStructure<Transaction> saveTransaction(@RequestParam("accountid") int accountid, @RequestBody Transaction transaction){
		Optional<Account> o=accountRepo.findById(accountid);
		ResponseStructure<Transaction> rs= new ResponseStructure<Transaction>();
		
		if(o.isPresent()) {
			Account account=o.get();
			
			if(transaction.getTransactionType().equalsIgnoreCase("deposit")) {
				account.setBalance(account.getBalance()+transaction.getBalance());
			}
			else if(transaction.getTransactionType().equalsIgnoreCase("withdraw")) {
				account.setBalance(account.getBalance()-transaction.getBalance());
			}
			accountRepo.save(account);
			
			transaction.setAccount(account);
			transaction.setTransactionDate(LocalDateTime.now());
			
			transactionRepo.save(transaction);
			
			rs.setStatusCode(HttpStatus.CREATED.value());
			rs.setData(transaction);
			rs.setMessage("Transaction created and balance updated successfully.....!");
		}
		else {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Account Not Found.....!");	
		}
		return rs;
		
	}
	
	
	//http://localhost:8080/api/bank/getTransactionByAccountId?accountid=1
	@GetMapping("/getTransactionByAccountId")
	public ResponseStructure<List<Transaction>> getTransactionsByAccountId(@RequestParam("accountid") int accountid){
		
		Optional<Account> o=accountRepo.findById(accountid);
		ResponseStructure<List<Transaction>> rs= new ResponseStructure<List<Transaction>>();
		
		if(o.isPresent()) {
			List<Transaction> transactions=transactionRepo.findByAccount(o.get());
			
			if(!transactions.isEmpty()) {
				rs.setStatusCode(HttpStatus.OK.value());
				rs.setData(transactions);
				rs.setMessage("Transactions found for the account ID: " + accountid);
			}
			else {
				 rs.setStatusCode(HttpStatus.NOT_FOUND.value());
				 rs.setMessage("No transactions found for the account ID: " + accountid);
			}
		
		}
		else {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
            rs.setMessage("Account not found!");
		}
		return rs;
	}
	

}
