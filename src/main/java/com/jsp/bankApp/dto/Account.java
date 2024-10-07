package com.jsp.bankApp.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountid;
	
	private String accountNumber;
	private String ifscNumber;
	private String accountType;
	private String branch;
	private double balance;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "customerid", nullable = false)
    private Customer customer;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Transaction> transactions;

	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIfscNumber() {
		return ifscNumber;
	}

	public void setIfscNumber(String ifscNumber) {
		this.ifscNumber = ifscNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
}
