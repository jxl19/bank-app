package com.jun.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jun.exceptions.UserNotFoundException;
import com.jun.services.CustomerService;

public class CustomerMenu extends MainMenu implements Menu{
	
	public CustomerService customerService;
	
	public CustomerMenu() {
		this.customerService = new CustomerService();
	}
	
	public void display() {
		
		int choice = 0;
		
		//add select customer account by name
		do {
			System.out.println("=== CUSTOMER MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Select Customer Account");
			System.out.println("2.) Log Out");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			switch (choice) {
				case 1: 
				try {
					getCustAccount();
				} catch (UserNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
					break;
				case 2:
					System.out.println("Logging out");
					super.display();
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while (choice != 2);
	}
	
	
	private void getCustAccount() throws UserNotFoundException, SQLException {
		
		List<String> ids = new ArrayList<>();
		ids = customerService.getCustomerCardNumber(loginId);
//		System.out.println(ids);
		
		int choice = 0;
		String ac;
		StringBuilder sb = new StringBuilder();
		sb.append("=== CUSTOMER ACCOUNTS===");
		sb.append(System.getProperty("line.separator"));
		sb.append("Select a customer account below");
		sb.append(System.getProperty("line.separator"));
		for (int i = 0; i <= ids.size(); i++) {
			if (i < ids.size()) sb.append(i+1 + ".) " + ids.get(i));
			else sb.append(i+1 + ".) Exit");
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
			this.getCustAccount();
		}

	}
}
