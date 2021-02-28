package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.UserNotFoundException;
import com.jun.model.Login;
import com.jun.services.LoginService;

public class MainMenu implements Menu {
	
	public LoginService loginService; 
	public static boolean isEmployee;
	public static int loginId;
	
	public MainMenu() {
		this.loginService = new LoginService();
	}

	public void display() {
		System.out.println("=== Welcome to Bank of Jun! ===");
		//TODO: need menu here to either create account or login
		getAccountType();
	}
	
	private void getAccountType() {
		Login login = null;
		
		System.out.println("Please Enter your username: ");
		String un = Menu.sc.nextLine();
		System.out.println("Please Enter your password: ");
		String pw = Menu.sc.nextLine();
		
		try {
			login = loginService.authenticateUser(un, pw);
		} catch (SQLException | UserNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		if (login == null) {
			this.getAccountType();
		} else {
			isEmployee = login.isEmployee();
			loginId = login.getLoginId();
			EmployeeMenu em = new EmployeeMenu();
			CustomerMenu cm = new CustomerMenu();
			if (isEmployee) {
				em.display();
			} else cm.display();
		}
	}
	
}
