package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.exceptions.InvalidEmailException;
import com.jun.model.Login;

public interface LoginDAO {
	public Login authenticateUser(String username, String password, Connection con) throws SQLException;
	public int createLogin(String username, String password, String email, Connection con) throws InvalidEmailException, SQLException;
	public boolean createUser(boolean isEmployee, int loginId, String firstName, String lastName, Connection con) throws SQLException;
	public boolean createCustomer(int loginId, int credit, Connection con) throws SQLException;
	public boolean createEmployee(int loginId, boolean isAdmin, Connection con) throws SQLException;
}
