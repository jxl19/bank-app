package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.InvalidEmailException;
import com.jun.exceptions.UserAlreadyExistsException;
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
		int choice = 0;
		do {
			System.out.println("========================= Welcome to Bank of Jun! =================================");
			System.out.println("||                              1.) Login                                        ||");
			System.out.println("||                              2.) Create Account                               ||");
			System.out.println("===================================================================================");
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {};

			switch (choice) {
			case 1:
				getAccountType();
				break;
			case 2:
				createUserAccount();
				break;
			}
		} while (choice != 3);
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
			CustomerMenu cm = new CustomerMenu(login.getLoginId());
			if (isEmployee) {
				em.display();
			} else
				cm.display();
		}
	}

	private void createUserAccount() {
		boolean invalidEmail = true;
		String email = "";
		System.out.println("Please Enter your desired username:");
		String un = Menu.sc.nextLine();
		System.out.println("Please Enter your password:");
		String pw = Menu.sc.nextLine();
		do {
			String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
			System.out.println("Please enter your email:");
			email = Menu.sc.nextLine();
			if (email.matches(regex)) {
				invalidEmail = false;
			} else {
				System.out.println("Email is invalid, please try again");
			}
		} while (invalidEmail);
		System.out.println("Please enter your first name:");
		String firstName = Menu.sc.nextLine();
		System.out.println("Please enter your last name:");
		String lastName = Menu.sc.nextLine();
		try {
			loginService.createUser(un, pw, firstName, lastName, email, false);
		} catch (InvalidEmailException | UserAlreadyExistsException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
