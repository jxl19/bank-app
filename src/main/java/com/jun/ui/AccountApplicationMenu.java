package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.services.ApplicationService;

public class AccountApplicationMenu implements Menu {

	public String firstName;
	public String lastName;
	public int creditScore; 
	public double initialBalance;
	public boolean isCheckingAccount;
	public ApplicationService applicationService;
	public AccountApplicationMenu(boolean isCheckingAccount) {
		this.applicationService = new ApplicationService();
		this.isCheckingAccount = isCheckingAccount;
	}
	
	public void display() {
		System.out.println("=== APPLY FOR A NEW ACCOUNT ===");
		int choice = 0;
		
		do {
			System.out.println("Please enter your starting balance for this account: ");
			System.out.println("1.) Exit");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {};
			
			switch (choice) {
				case 1: 
					break;
				default: 
				try {
					String application = applicationService.applyForNewAccount(MainMenu.loginId, choice, isCheckingAccount);
					System.out.println(application);
					choice = 1;
				} catch (SQLException | InvalidBalanceException e) {
					System.out.println(e.getMessage());
				} 
					
			}
		} while (choice != 1);
	}

}
