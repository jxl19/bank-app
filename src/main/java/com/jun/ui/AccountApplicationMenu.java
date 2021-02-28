package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.services.ApplicationService;

public class AccountApplicationMenu implements Menu {

//	public String cardNumber; //we'll create in dao, 
	//we'll need to tie it in with a customer(user) also at the same time,
	//also if the person creating this account doesnt already have a person id, create it.. check 
	// user applies > fn,ln,credit > approve > create card > check in current login/account if we have this fn+ln combo,
	//if not create new person and tie it with this login
	public String firstName;
	public String lastName;
	public int creditScore; // we'll use this to let employee judge
	public double initialBalance;
	public ApplicationService applicationService;
	//MainMenu.loginId
	public AccountApplicationMenu() {
		this.applicationService = new ApplicationService();
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
					String application = applicationService.applyForNewAccount(MainMenu.loginId, choice);
					System.out.println(application);
					choice = 1;
				} catch (SQLException | InvalidBalanceException e) {
					System.out.println(e.getMessage());
				} 
					
			}
		} while (choice != 1);
	}

}
