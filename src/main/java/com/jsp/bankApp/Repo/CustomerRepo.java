package com.jsp.bankApp.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.bankApp.dto.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	
	@Query
	Optional<Customer> findBycustomerName(String customerName);
	
	

	void deleteBycustomerName(String customerName);
	
	@Query
	Optional<Customer> findByPanNumber(String panNumber);
	
	@Query
	Optional<Customer> findByAadharNumber(String aadharNumber);
}
