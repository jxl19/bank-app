package com.jun.ui;

public class ReviewCustomerMenu implements Menu{
	
	public ReviewCustomerMenu() {
		
	}
	
	public void display() {
		
		int choice = 0;
		
		do {
			System.out.println("===REVIEW CUSTOMER===");
			System.out.println("Enter customer id or enter 1 to exit");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			switch (choice) {
				case 1:
					EmployeeMenu em = new EmployeeMenu();
					em.display();
				default: 
					CustomerLookupMenu ccm = new CustomerLookupMenu(choice);
					ccm.display();
			}
			
		} while(choice != 1);
	}
	
}
