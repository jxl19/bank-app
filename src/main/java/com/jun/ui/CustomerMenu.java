package com.jun.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jun.exceptions.CardNotFoundException;
import com.jun.exceptions.UserNotFoundException;
import com.jun.services.CardService;
import com.jun.services.CustomerService;

public class CustomerMenu implements Menu{
	
	public CustomerService customerService;
	public CardService cardService;
	
	public CustomerMenu() {
		this.customerService = new CustomerService();
		this.cardService = new CardService();
	}
	
	public void display() {
		
		int choice = 0;
		
		do {
			System.out.println("=== CUSTOMER MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Select Bank Account");
			System.out.println("2.) Apply for new Bank account");
			System.out.println("3.) Log Out");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			switch (choice) {
				case 1: 
					try {
						getCustAccount(MainMenu.loginId);
					} catch (UserNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					break;
				case 2: 
					System.out.println("Creating new account..");
					AccountApplicationMenu aam = new AccountApplicationMenu();
					aam.display();
					break;
				case 3:
					System.out.println("Logging out");
					MainMenu mm = new MainMenu();
					mm.display();
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while (choice != 3);
	}
	
	void getCustAccount(int id) throws UserNotFoundException, SQLException {
		
		List<String> ids = new ArrayList<>();
		ids = customerService.getCustomerCardNumber(id);
		
		int choice = 0;
		double bal = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("=== BANK ACCOUNTS===");
		sb.append(System.getProperty("line.separator"));
		sb.append("Select a bank account below");
		sb.append(System.getProperty("line.separator"));
		for (int i = 0; i <= ids.size(); i++) {
			if (i < ids.size()) {
				sb.append(i+1 + ".) " + ids.get(i));
				try {
					bal = cardService.getBalanceByCardNum(ids.get(i));
				} catch (SQLException | CardNotFoundException e) {
					System.out.println(e.getMessage());
				} 
				sb.append(" - Account Balance: " + bal);
			} else sb.append(i+1 + ".) Exit");
			sb.append(System.getProperty("line.separator"));
		}
		System.out.println(sb);
		
		try { 
			choice = Integer.parseInt(Menu.sc.nextLine());
		} catch (NumberFormatException e) {} 
		
		//exit menu
		if (choice - 1 == ids.size()) {
			CustomerMenu cm = new CustomerMenu();
			System.out.println("Exiting..");
			cm.display();
		}
		
		if (choice <= ids.size() & choice != 0) {
			System.out.println(ids.get(choice - 1));
			String acc = ids.get(choice - 1);
			AccountMenu am = new AccountMenu(acc);
			am.display();
		} else {
			System.out.println("invalid");
			this.getCustAccount(id);
		}

	}
}
