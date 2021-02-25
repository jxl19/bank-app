package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jun.model.User;

public class LoginDAOImpl implements LoginDAO{

	@Override
	public User authenticateUser(String username, String password, Connection con) throws SQLException{
		User user = null;
		
		String sql = "SELECT * FROM bank.person WHERE login_id = (SELECT login_id FROM bank.login WHERE username =? AND pass =?)";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.setString(2, password);


		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			Boolean isAdmin = rs.getBoolean("isAdmin");
			int userId = rs.getInt("person_id");
			int loginId = rs.getInt("login_id");
			double balance = rs.getDouble("balance");
			
			user = new User(isAdmin, userId, loginId, balance);
		}
		
		return user;
	}
	
}
