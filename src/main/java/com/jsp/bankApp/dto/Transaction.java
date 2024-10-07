package com.jsp.bankApp.dto;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionid;
	
	private LocalDateTime transactionDate;
	private String transactionType;
	private double balance;
	
	
	@ManyToOne
    @JoinColumn(name = "accountid", nullable = false)
    private Account account;


	public int getTransactionid() {
		return transactionid;
	}


	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}


	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(LocalDateTime localDateTime) {
		this.transactionDate = localDateTime;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}
