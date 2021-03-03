package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.jun.dao.CustomerDAO;
import com.jun.dao.CustomerDAOImpl;
import com.jun.exceptions.UserNotFoundException;
import com.jun.model.Customer;
import com.jun.util.ConnectionUtil;

public class CustomerService {

	public CustomerDAO customerDAO; 
	
	public CustomerService() {
		this.customerDAO = new CustomerDAOImpl();
	}
	//TODO: will not need... at the moment this is to check if we can do another query... redundant
	public List<String> getCustomerCardNumber(int id) throws UserNotFoundException, SQLException {
			String s = "NEED TO UPDATE RANDOM STRING HERE";
		try (Connection con = ConnectionUtil.getConnection()) {
			Customer customer;
			
			customer = customerDAO.getCustomerById(id, con);
			if(customer == null) {
				throw new UserNotFoundException("There are no accounts under this user");
			}
			
			return customer.getCardNo();
		}
		
	}
}
