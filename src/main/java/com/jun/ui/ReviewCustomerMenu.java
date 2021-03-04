package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.UserNotFoundException;
import com.jun.services.CustomerService;

public class ReviewCustomerMenu implements Menu{
	
	CustomerService customerService;
	
	public ReviewCustomerMenu() {
		this.customerService = new CustomerService();
	}
	
	public void display() {
		
		int choice = 0;
		
		do {
			System.out.println("===================================REVIEW CUSTOMER===================================");
			System.out.println("||                         Enter a customer id or go back                          ||");
			System.out.println("||                               Enter customer id:                                ||");
			System.out.println("||                               1.) Exit                                          ||");
			System.out.println("=====================================================================================");
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			switch (choice) {
				case 1:
					EmployeeMenu em = new EmployeeMenu();
					em.display();
					break;
				default: 
					boolean accountExists = false;
				try {
					accountExists = customerService.getCustomerById(choice);
				} catch (UserNotFoundException | SQLException e) {
					System.out.println(e.getMessage());
				}
				if (accountExists) {
					CustomerLookupMenu ccm = new CustomerLookupMenu(choice);
					ccm.display();
				}
				break;
			}
			
		} while(choice != 1);
	}
	
}
