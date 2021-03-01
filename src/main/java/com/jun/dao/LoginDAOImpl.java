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
		
		String sql = "SELECT * FROM bank.users WHERE login_id = (SELECT login_id FROM bank.login WHERE username =? AND pass =?)";
		
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
	
}
