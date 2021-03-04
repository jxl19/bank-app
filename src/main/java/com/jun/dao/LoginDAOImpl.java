package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jun.model.Login;

public class LoginDAOImpl implements LoginDAO{

	@Override
	public Login authenticateUser(String username, String password, Connection con) throws SQLException{
		Login login = null;
		
		String sql = "SELECT * FROM bank.users WHERE login_id = (SELECT login_id FROM bank.login WHERE username = ? AND pass = ?)";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.setString(2, password);


		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			boolean isEmployee = rs.getBoolean("is_employee");
			int loginId = rs.getInt("login_id");
			
			login = new Login(isEmployee, loginId);
		}

		return login;
	}

	@Override
	public int createLogin(String username, String password, String email, Connection con) throws SQLException {
		String sql = "INSERT INTO bank.login (username, email, pass) VALUES (?,?,?)";
		String getIdSql = "SELECT login_id FROM bank.login WHERE username = ?";
		int loginId = 0;
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, email);
		ps.setString(3, password);
		ps.executeUpdate();
		
		PreparedStatement idPS = con.prepareStatement(getIdSql);
		idPS.setString(1, username);
		ResultSet rs = idPS.executeQuery();
		
		if (rs.next()) {
			loginId = rs.getInt("login_id");
		} else {
			throw new SQLException("There was a problem creating account");
		}
		
		return loginId;
	}

	@Override
	public boolean createUser(boolean isEmployee, int loginId, String firstName, String lastName, Connection con)
			throws SQLException {
		String sql = "INSERT INTO bank.users (is_employee, login_id, first_name, last_name) VALUES (?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setBoolean(1, isEmployee);
		ps.setInt(2, loginId);
		ps.setString(3, firstName);
		ps.setString(4, lastName);
		ps.executeUpdate();
		return true;
	}
	
	@Override
	public boolean createCustomer(int loginId, int credit, Connection con) throws SQLException {
		String sql = "INSERT INTO bank.customer (login_id, credit) VALUES (?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, loginId);
		ps.setInt(2, credit);
		ps.executeUpdate();
		return true;
	}

	@Override
	public boolean createEmployee(int loginId, boolean isAdmin, Connection con) throws SQLException {
		String sql = "INSERT INTO bank.employee (login_id, isAdmin) VALUES (?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, loginId);
		ps.setBoolean(2, isAdmin);
		ps.executeUpdate();
		
		return true;
	}

	
}
