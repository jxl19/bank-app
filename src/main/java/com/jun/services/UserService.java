package com.jun.services;

import com.jun.dao.LoginDAO;
import com.jun.dao.LoginDAOImpl;

public class UserService {
	public LoginDAO loginDAO;
	
	public UserService() {
		this.loginDAO = new LoginDAOImpl();
	}
}
