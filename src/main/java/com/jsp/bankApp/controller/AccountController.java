package com.jsp.bankApp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.bankApp.Repo.AccountRepo;
import com.jsp.bankApp.Repo.CustomerRepo;
import com.jsp.bankApp.Repo.TransactionRepo;
import com.jsp.bankApp.dto.Account;
import com.jsp.bankApp.dto.Customer;
import com.jsp.bankApp.dto.Transaction;
import com.jsp.bankApp.helper.ResponseStructure;


@RestController
@CrossOrigin
@RequestMapping("/api/bank")
public class AccountController {
	
	@Autowired
	AccountRepo accountRepo;
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	TransactionRepo transactionRepo;

	
	//http://localhost:8080/api/bank/saveAccount?customerid=1
	@PostMapping("saveAccount")
	public ResponseStructure<Account> saveAccount(@RequestParam("customerid") int customerid,@RequestBody Account account){
		Optional<Customer> customer =customerRepo.findById(customerid);
		ResponseStructure<Account> rs= new ResponseStructure<Account>();
		
		if(customer.isPresent()) {
			account.setCustomer(customer.get());
			
			Account savedAccount=accountRepo.save(account);
			
			rs.setStatusCode(HttpStatus.CREATED.value());
			rs.setData(savedAccount);
			rs.setMessage("Account created Successfully......!");
		}
		else {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
	        rs.setMessage("Customer not found!");
		}
		return rs;
	}
	
	
	//http://localhost:8080/api/bank/getAllAccounts
	@GetMapping("/getAllAccounts")
	public ResponseStructure<List<Account>> getAllAccounts(){
		List<Account> account=accountRepo.findAll();
		
		ResponseStructure<List<Account>> rs= new ResponseStructure<List<Account>>();
		
		if(!account.isEmpty()) {
			rs.setStatusCode(HttpStatus.FOUND.value());
			rs.setData(account);
			rs.setMessage("Fetch all Details successfully.....!");
		}
		else {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage(" Details are Not Found.....!");
		}
		return rs;
	}
	
	
	//http://localhost:8080/api/bank/getAccountById?accoutId=1
	@GetMapping("/getAccountById")
	public ResponseStructure<Account> getAccountById(@RequestParam("accountid") int accountid ){
		Optional<Account> o=accountRepo.findById(accountid);
		
		if(o.isPresent()) {
			Account account=o.get();
			ResponseStructure<Account> rs= new ResponseStructure<Account>();
			rs.setStatusCode(HttpStatus.FOUND.value());
			rs.setData(account);
			rs.setMessage("Acount Found By ID Successfully......!");
			return rs;
		}
		else {
			ResponseStructure<Account> rs= new ResponseStructure<Account>();
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Account Id Not Found");
			return rs;
		}
	}
	
	
	//http://localhost:8080/api/bank/getAccountBYAccountNumber?accountNumber="accountnumber"
	@GetMapping("/getAccountByAccountNumber")
	public ResponseStructure<Account> getAccountByAccountName(@RequestParam("accountNumber") String accountNumber){
		Optional<Account> o=accountRepo.findByAccountNumber(accountNumber);
		
		if(o.isPresent()) {
			Account account=o.get();
			ResponseStructure<Account> rs= new ResponseStructure<Account>();
			rs.setStatusCode(HttpStatus.FOUND.value());
			rs.setData(account);
			rs.setMessage("Account Found By AccountNumber Successfully......!");
			return rs;
		}
		else {
			ResponseStructure<Account> rs= new ResponseStructure<Account>();
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("AccountNumber Not Found.....!");
			return rs;
		}
	}
	
	
	//http://localhost:8080/api/bank/updateAccount
	@PutMapping("/updateAccount")
	public ResponseStructure<Account> updateAccount(@RequestParam("accountid") int accountid, @RequestParam("customerid") int customerid, @RequestBody Account account){
		Optional<Account> o=accountRepo.findById(accountid);
		
		if(o.isPresent()) {
			Account updateAccount=o.get();
			
			updateAccount.setAccountNumber(account.getAccountNumber());
			updateAccount.setIfscNumber(account.getIfscNumber());
			updateAccount.setAccountType(account.getAccountType());
			updateAccount.setBranch(account.getBranch());
			updateAccount.setBalance(account.getBalance());
			
			Optional<Customer> c= customerRepo.findById(customerid);
			if(c.isPresent()) {
				Customer customer=c.get();
				updateAccount.setCustomer(c.get());
			}
			else {
				ResponseStructure<Account> rs= new ResponseStructure<Account>();
				rs.setStatusCode(HttpStatus.NOT_FOUND.value());
				rs.setMessage("Customer Not Found....!");
			}
			
			accountRepo.save(updateAccount);
			
			ResponseStructure<Account> rs= new ResponseStructure<Account>();
			rs.setStatusCode(HttpStatus.CREATED.value());
			rs.setData(updateAccount);
			rs.setMessage("Account Update Successfully.....!");
			return rs;
		}
		else {
			ResponseStructure<Account> rs= new ResponseStructure<Account>();
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Account Not Found.....!");
			return rs;
		}
	}
	
	
	//http://localhost:8080/api/bank/deleteAccount
	@DeleteMapping("deleteAccount")
	public ResponseStructure<Account> deleteAccount(@RequestParam("accountid") int accountid){
		
		accountRepo.deleteById(accountid);
		
		ResponseStructure<Account> rs= new ResponseStructure<Account>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Account Data Successfully......!");
		return rs;
	}
	
	
	
	//http://localhost:8080/api/bank/transfer?fromAccountId=3&toAccountId=4&balance=1000
	@PostMapping("/transfer")
	public ResponseStructure<String> transferMoney(@RequestParam("fromAccountId") int fromAccountId,
	                                               @RequestParam("toAccountId") int toAccountId,
	                                               @RequestParam("balance") double balance) {
	    ResponseStructure<String> rs = new ResponseStructure<>();
	    
	    // Fetch the source account
	    Optional<Account> sourceAccountOpt = accountRepo.findById(fromAccountId);
	    
	    // Fetch the destination account
	    Optional<Account> destinationAccountOpt = accountRepo.findById(toAccountId);
	    
	    if (sourceAccountOpt.isPresent() && destinationAccountOpt.isPresent()) {
	        Account sourceAccount = sourceAccountOpt.get();
	        Account destinationAccount = destinationAccountOpt.get();
	        
	        // Check if the source account has enough balance
	        if (sourceAccount.getBalance() >= balance) {
	            // Perform the transfer
	            sourceAccount.setBalance(sourceAccount.getBalance() - balance);
	            destinationAccount.setBalance(destinationAccount.getBalance() + balance);
	            
	            // Save the updated accounts
	            accountRepo.save(sourceAccount);
	            accountRepo.save(destinationAccount);
	            
	            // Create transaction records
	            Transaction debitTransaction = new Transaction();
	            debitTransaction.setTransactionDate(LocalDateTime.now());
	            debitTransaction.setTransactionType("DEBIT");
	            debitTransaction.setBalance(-balance);
	            debitTransaction.setAccount(sourceAccount);
	            transactionRepo.save(debitTransaction);
	            
	            Transaction creditTransaction = new Transaction();
	            creditTransaction.setTransactionDate(LocalDateTime.now());
	            creditTransaction.setTransactionType("CREDIT");
	            creditTransaction.setBalance(balance);
	            creditTransaction.setAccount(destinationAccount);
	            transactionRepo.save(creditTransaction);
	            
	            rs.setStatusCode(HttpStatus.OK.value());
	            rs.setMessage("Money transferred successfully!");
	            rs.setData("Transfer of " + balance + " from Account ID " + fromAccountId + " to Account ID " + toAccountId + " completed.");
	        } else {
	            rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
	            rs.setMessage("Insufficient balance in the source account!");
	        }
	    } else {
	        rs.setStatusCode(HttpStatus.NOT_FOUND.value());
	        rs.setMessage("One or both accounts not found!");
	    }
	    
	    return rs;
	}


	
}
