package com.jun.ui;

public class EmployeeMenu implements Menu{
	
	public EmployeeMenu() {
		
	}
	
	public void display() {
		int choice = 0;
		
		do {
			System.out.println("==== EMPLOYEE MENU ====");
			System.out.println("Please select an option below");
			System.out.println("1.) Lookup User");
			System.out.println("2.) Check new applications");
			System.out.println("3.) Log out");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			switch (choice) {
				case 1: 
					ReviewCustomerMenu rcm = new ReviewCustomerMenu();
					rcm.display();
					break;
				case 2: 
					ReviewApplicationMenu ram = new ReviewApplicationMenu();
					ram.display();
					break;
				case 3:
					MainMenu mm = new MainMenu();
					mm.display();
				default:
					System.out.println("No valid case, please try again");
			}
			
		} while (choice != 3);
	}
	
}
