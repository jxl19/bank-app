package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.CustomerDAO;
import com.jun.dao.CustomerDAOImpl;
import com.jun.exceptions.UserNotFoundException;
import com.jun.util.ConnectionUtil;

public class CustomerService {

	public CustomerDAO customerDAO; 
	
	public CustomerService() {
		this.customerDAO = new CustomerDAOImpl();
	}
	
	public boolean getCustomerById(int id) throws UserNotFoundException, SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {
			if (customerDAO.getCustomerAccountsById(id, con) == null) {
				throw new UserNotFoundException("This user id does not exist");				
			}
			return true;
		}
	}
	
}
