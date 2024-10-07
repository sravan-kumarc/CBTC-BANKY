package com.jsp.bankApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.bankApp.Repo.CustomerRepo;
import com.jsp.bankApp.dto.Customer;
import com.jsp.bankApp.helper.ResponseStructure;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/api/bank")
public class CustomerController {
	
	@Autowired
	CustomerRepo customerRepo;
	
	//http://localhost:8080/api/bank/saveCustomer
	@PostMapping("/saveCustomer")
	public ResponseStructure<Customer> saveCustomer(@RequestBody Customer customer){
		customerRepo.save(customer);
		
		ResponseStructure<Customer> rs=new ResponseStructure<Customer>();
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setData(customer);
		rs.setMessage("Customer Data Saved Successfully....!");
		return rs;
	}
	
	//http://localhost:8080/api/bank/getAllCustomer
	@GetMapping("/getAllCustomer")
	public ResponseStructure<List<Customer>> getAllCustomer(){
		List<Customer> customers=customerRepo.findAll();
		ResponseStructure<List<Customer>> rs= new ResponseStructure<List<Customer>>();
		
		if(!customers.isEmpty()) {
			rs.setStatusCode(HttpStatus.FOUND.value());
			rs.setData(customers);
			rs.setMessage("Customer Details are Found....!");
		}
		else {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Customer Details Are Not xFound....!");
		}
		return rs;
	}
	
	//http://localhost:8080/api/bank/getCustomerByID?id=1
	@GetMapping("/getCustomerById")
	public ResponseStructure<Customer> getCustomerById(@RequestParam("id") int id){
		Optional<Customer> o=customerRepo.findById(id);
		if(o.isPresent()) {
			Customer customer=o.get();
			ResponseStructure<Customer> rs=new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.FOUND.value());
			rs.setData(customer);
			rs.setMessage("Customer Details are Found....!");
			return rs;
		}
		else {
			ResponseStructure<Customer> rs=new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Customer Details are NOT Found ....!");
			return rs;
		}
	}
	
	//http://localhost:8080/api/bank/getCustomerByName?customerName="customername"
	@GetMapping("/getCustomerName")
	public ResponseStructure<Customer> getCustomerName(@RequestParam("customerName") String customerName){
		Optional<Customer> o=customerRepo.findBycustomerName(customerName);
		if(o.isPresent()) {
			Customer customer=o.get();
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.FOUND.value());
			rs.setData(customer);
			rs.setMessage("Customer Details Is Found.....!");
			return rs;
		}
		else {
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Customer Details are NOT Found.....!");
			return rs;
		}
	}
	
	//http://localhost:8080/api/bank/getCustomerAadhar?aadharNumber="aadharNumber"
	@GetMapping("/getCustomerAadhar")
	public ResponseStructure<Customer> getCustomerAadhar(@RequestParam("aadharNumber") String aadharNumber){
		Optional<Customer> o=customerRepo.findByAadharNumber(aadharNumber);
		if(o.isPresent()) {
			Customer customer=o.get();
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.FOUND.value());
			rs.setData(customer);
			rs.setMessage("Customer Details Is Found.....!");
			return rs;
		}
		else {
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Customer Details are  NOT Found.....!");
			return rs;
		}
	}
	
	//http://localhost:8080/api/bank/getCustomerPan?panNumber="pannumber"
	@GetMapping("/getCustomerPan")
	public ResponseStructure<Customer> getCustomerPan(@RequestParam("panNumber") String panNumber){
		Optional<Customer> o=customerRepo.findByPanNumber(panNumber);
		if(o.isPresent()) {
			Customer customer=o.get();
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.FOUND.value());
			rs.setData(customer);
			rs.setMessage("Customer Details are Found.....!");
			return rs;
		}
		else {
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Customer Details are NOT Found.....!");
			return rs;
		}
	}
	
	//http://localhost:8080/api/bank/updateCustomer?id=1
	@PutMapping("updateCustomer")
	public ResponseStructure<Customer> updateCustomer(@RequestParam("id")int id, @RequestBody Customer customer){
		Optional<Customer> o=customerRepo.findById(id);
		if(o.isPresent()) {
			Customer updateCustomer=o.get();
			updateCustomer.setCustomerName(customer.getCustomerName());
			updateCustomer.setAadharNumber(customer.getAadharNumber());
			updateCustomer.setPanNumber(customer.getPanNumber());
			updateCustomer.setAddress(customer.getAddress());
			updateCustomer.setEmail(customer.getEmail());
			updateCustomer.setPhone(customer.getPhone());
			
			customerRepo.save(updateCustomer);
			
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.ACCEPTED.value());
			rs.setData(updateCustomer);
			rs.setMessage("Customer Data Update Successfully....!");
			return rs;
		}
		else {
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
			rs.setMessage("Customer Data Not Found....!");
			return rs;
		}
	}
	
	//http://localhost:8080/api/bank/updateCustomerByName?ccustomerName="customername"
	@PutMapping("/updateCustomerByName")
	public ResponseStructure<Customer> updateCustomerByName(@RequestParam("customerName") String CustomerName, @RequestBody Customer customer){
		Optional<Customer> o=customerRepo.findBycustomerName(CustomerName);
		
		if(o.isPresent()) {
			Customer updateCustomer=o.get();
			updateCustomer.setCustomerName(customer.getCustomerName());
			updateCustomer.setAadharNumber(customer.getAadharNumber());
			updateCustomer.setPanNumber(customer.getPanNumber());
			updateCustomer.setAddress(customer.getAddress());
			updateCustomer.setEmail(customer.getEmail());
			updateCustomer.setPhone(customer.getPhone());
			
			customerRepo.save(updateCustomer);
			
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.ACCEPTED.value());
			rs.setData(updateCustomer);
			rs.setMessage("Customer Data Update Successfully....!");
			return rs;
		}
		else {
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
			rs.setMessage("Customer Data Not Found....!");
			return rs;
		}
	}
	
	//http://localhost:8080/api/bank/updateByPanNumber?panNumber="panNumber"
	@PutMapping("/updateByPanNumber")
	public ResponseStructure<Customer> updateByPanNumber(@RequestParam("panNumber") String panNumber, @RequestBody Customer customer){
		Optional<Customer> o= customerRepo.findByPanNumber(panNumber);
		
		if(o.isPresent()) {
			Customer updateCustomer=o.get();
			updateCustomer.setCustomerName(customer.getCustomerName());
			updateCustomer.setAadharNumber(customer.getAadharNumber());
			updateCustomer.setPanNumber(customer.getPanNumber());
			updateCustomer.setAddress(customer.getAddress());
			updateCustomer.setEmail(customer.getEmail());
			updateCustomer.setPhone(customer.getPhone());
			
			customerRepo.save(updateCustomer);
			
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.CREATED.value());
			rs.setData(updateCustomer);
			rs.setMessage("Customer Data Update Successfully....!");
			return rs;
		}
		else {
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Customer Data Not Found.....!");
			return rs;
		}
	}
	
	//http://localhost:8080/api/bank/updateByAadharNumber?aadharNumber="aadharnumber"
	@PutMapping("/updateByAadharNumber")
	public ResponseStructure<Customer> updateByAadharNumber(@RequestParam("aadharNumber") String aadharNumber, @RequestBody Customer customer){
		Optional<Customer> o= customerRepo.findByAadharNumber(aadharNumber);
		
		if(o.isPresent()) {
			Customer updateCustomer=o.get();
			updateCustomer.setCustomerName(customer.getCustomerName());
			updateCustomer.setAadharNumber(customer.getAadharNumber());
			updateCustomer.setPanNumber(customer.getPanNumber());
			updateCustomer.setAddress(customer.getAddress());
			updateCustomer.setEmail(customer.getEmail());
			updateCustomer.setPhone(customer.getPhone());
			
			customerRepo.save(updateCustomer);
			
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.CREATED.value());
			rs.setData(updateCustomer);
			rs.setMessage("Customer Data Update Successfully....!");
			return rs;
		}
		else {
			ResponseStructure<Customer> rs= new ResponseStructure<Customer>();
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Customer Data Not Found.....!");
			return rs;
		}
	}
	
	//http://localhost:8080/api/bank/deleteCustomer?id=1
	@DeleteMapping("deleteCustomer")
	public ResponseStructure<Customer> deleteCustomer(@RequestParam("id") int id){
		customerRepo.deleteById(id);
		
		ResponseStructure<Customer> rs=new ResponseStructure<Customer>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Customer Data Deleted Successfully...!");
		return rs;
	}
	
	
	//http://localhost:8080/api/bank/deleteCustomerName?customerName="customerName"
	@Transactional
	@DeleteMapping("/deleteCustomerName")
	public ResponseStructure<Customer> deleteCustomerName(@RequestParam("customerName") String customerName){
		customerRepo.deleteBycustomerName(customerName);
		
		ResponseStructure<Customer> rs=new ResponseStructure<Customer>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Customer Data Deleted Successfully...!");
		return rs;
	}
	
	
	
	
}
