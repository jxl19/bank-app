package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.exceptions.UserNotFoundException;
import com.jun.model.Customer;

public interface CustomerDAO {
	public Customer getCustomerAccountsById(int userId, Connection con) throws SQLException; 
	public Customer getCustomer(int userId, Connection con) throws SQLException;
	public boolean checkValidUnsername(String username, Connection con) throws SQLException;
	public boolean updateCustomerBalance(int userId, double amount, double currBal, Connection con) throws InvalidBalanceException, UserNotFoundException, SQLException;
}
