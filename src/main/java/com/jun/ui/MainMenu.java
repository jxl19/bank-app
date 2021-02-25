package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.UserNotFoundException;
import com.jun.model.User;
import com.jun.services.UserService;

public class MainMenu implements Menu {
	
	public UserService userService; 
	
	public MainMenu() {
		this.userService = new UserService();
	}

	public void display() {
		System.out.println("=== Welcome to Bank of Jun! ===");
		getAccountType();
	}
	
	private void getAccountType() {
		boolean isAdmin = true;
		User user = null;
		
		System.out.println("Please Enter your username: ");
		String un = Menu.sc.nextLine();
		System.out.println("Please Enter your password: ");
		String pw = Menu.sc.nextLine();
		
		try {
			user = userService.authenticateUser(un, pw);
		} catch (SQLException | UserNotFoundException e) {
//			System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
			System.out.println(e.getMessage());
		}
		
		if (user == null) {
			this.getAccountType();
		} else {
			isAdmin = user.getIsAdmin();
			EmployeeMenu em = new EmployeeMenu();
			CustomerMenu um = new CustomerMenu();
			if (isAdmin) {
				em.display();
			} else um.display();
		}
	}
	
}
