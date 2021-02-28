package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.UserNotFoundException;

public class ReviewCustomerMenu implements Menu{
	
	public ReviewCustomerMenu() {
		
	}
	
	public void display() {
		
		int choice = 0;
		
		do {
			
			System.out.println("Enter customer id:");
			System.out.println("1.) exit");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			switch (choice) {
				case 1:
					break;
				default: 
					CurrentCustomerMenu ccm = new CurrentCustomerMenu();
					try {
						ccm.getCustAccount(choice);
					} catch (SQLException | UserNotFoundException e) {
						System.out.println(e.getMessage());
					} 
			}
			System.out.println(choice);
			
		} while(choice != 1);
	}
	
}
