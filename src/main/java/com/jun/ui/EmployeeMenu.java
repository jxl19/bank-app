package com.jun.ui;

public class EmployeeMenu implements Menu{
	
	public EmployeeMenu() {
		
	}
	
	public void display() {
		int choice = 0;
		
		do {
			System.out.println("==== EMPLOYEE MENU ====");
			System.out.println("Please select an option below");
			System.out.println("1.) Check user account");
			System.out.println("2.) Check new applications");
			System.out.println("3.) Log out");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			switch (choice) {
				case 1: 
					System.out.println("checking user account");
					ReviewCustomerMenu rcm = new ReviewCustomerMenu();
					rcm.display();
					break;
				case 2: 
					System.out.println("checking new applications");
					ReviewApplicationMenu ram = new ReviewApplicationMenu();
					ram.display();
					break;
				case 3:
					System.out.println("exiting employee");
					MainMenu mm = new MainMenu();
					mm.display();
				default:
					System.out.println("No valid case, please try again");
			}
			
		} while (choice != 3);
	}
	
}
