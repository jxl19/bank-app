package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;

import com.jun.dao.CustomerDAO;
import com.jun.dao.CustomerDAOImpl;
import com.jun.dao.LogDAO;
import com.jun.dao.LogDAOImpl;
import com.jun.dao.LoginDAO;
import com.jun.dao.LoginDAOImpl;
import com.jun.exceptions.InvalidEmailException;
import com.jun.exceptions.UserAlreadyExistsException;
import com.jun.exceptions.UserNotFoundException;
import com.jun.model.Login;
import com.jun.util.ConnectionUtil;

public class LoginService {
	public LoginDAO loginDAO;
	public LogDAO logDAO;
	public CustomerDAO customerDAO;
	public LoginService() {
		this.loginDAO = new LoginDAOImpl();
		this.logDAO = new LogDAOImpl();
		this.customerDAO = new CustomerDAOImpl();
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
	
	//TODO: admin can create employee
	public boolean createUser(String username, String password, String firstName, String lastName, String email, boolean isEmployee) throws InvalidEmailException, UserAlreadyExistsException, SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false);
			if (customerDAO.checkValidUnsername(username, con)) {
				int userId = loginDAO.createLogin(username, password, email, con);
				if (userId == (int)userId) {
					if (!isEmployee) {
						int userCredit = generateCredit();
						System.out.println("generating user credit:" + userCredit);
						loginDAO.createUser(isEmployee, userId, firstName, lastName, con);
						loginDAO.createCustomer(userId, userCredit, con);
					} else {
						loginDAO.createUser(isEmployee, userId, firstName, lastName, con);
						loginDAO.createEmployee(userId, false, con);
					}
				}
			} else {
				throw new UserAlreadyExistsException("Username is already being used, please try again with another usename");
			}
			con.commit();
			return true;
		}
	}
	
	//generates random credit score based off average credit of people
	private int generateCredit() {
		int creditPlacement = ThreadLocalRandom.current().nextInt(1,100);
		int userCredit = 0;
		if(creditPlacement <= 16) {
			userCredit = ThreadLocalRandom.current().nextInt(300,579);
		} else if (creditPlacement <= 34) {
			userCredit = ThreadLocalRandom.current().nextInt(580,669);
		} else if (creditPlacement <= 55) {
			userCredit = ThreadLocalRandom.current().nextInt(670,739);
		} else if (creditPlacement <= 80) {
			userCredit = ThreadLocalRandom.current().nextInt(740,799);
		} else userCredit = ThreadLocalRandom.current().nextInt(800,850);
		return userCredit;
	}
}
