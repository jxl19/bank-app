package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.jun.dao.LogDAO;
import com.jun.dao.LogDAOImpl;
import com.jun.dao.LoginDAO;
import com.jun.dao.LoginDAOImpl;
import com.jun.exceptions.UserNotFoundException;
import com.jun.model.Login;
import com.jun.util.ConnectionUtil;

public class LoginService {
	public LoginDAO loginDAO;
	public LogDAO logDAO;
	
	public LoginService() {
		this.loginDAO = new LoginDAOImpl();
		this.logDAO = new LogDAOImpl();
	}
	
	public LoginService(LoginDAO loginDAO, LogDAO logDAO) {
		this.loginDAO = loginDAO;
		this.logDAO = logDAO;
	}
	
	private static Logger log = Logger.getLogger(LoginService.class);
	
	public Login authenticateUser(String username, String password) throws UserNotFoundException, SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {
			Login login;
			
			login = loginDAO.authenticateUser(username, password, con);
			
			if (login == null) {
				log.error("Error logging in");
				throw new UserNotFoundException("Invalid user or password. Please try again");
			}
			String action = "signed on";
			logDAO.logUserAction(login.getLoginId(), action, con);
			log.info(username + " successfully logged on");
			return login;
		} 
	}
}
