package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.model.Customer;

public interface CustomerDAO {
	public Customer getCustomerById(int userId, Connection con) throws SQLException; 
	public Customer getCustomerBalance(int userId, Connection con) throws SQLException;
}
