package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.LoginDAO;
import com.jun.dao.LoginDAOImpl;
import com.jun.exceptions.UserNotFoundException;
import com.jun.model.User;
import com.jun.util.ConnectionUtil;
//
//public class UserService {
//	public LoginDAO loginDAO;
//	
//	public UserService() {
//		this.loginDAO = new LoginDAOImpl();
//	}
//	
//	public User authenticateUser(String username, String password) throws UserNotFoundException, SQLException {
//		try (Connection con = ConnectionUtil.getConnection()) {
//			User user;
//			
//			user = loginDAO.authenticateUser(username, password, con);
//			
//			if (user == null) {
//				throw new UserNotFoundException("Invalid user or password. Please try again");
//			}
//			
//			return user;
//		} 
//	}
//}
