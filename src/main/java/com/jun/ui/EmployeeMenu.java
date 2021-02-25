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
			System.out.println("3.) Exit");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			switch (choice) {
				case 1: 
					//another menu for user accounts and how to handle
					System.out.println("checking user account");
					CheckUserAccount cua = new CheckUserAccount();
					cua.display();
					break;
				case 2: 
					//anothger menu for new applicants and how to handle
					System.out.println("checking new applications");
					CheckNewApplications cna = new CheckNewApplications();
					cna.display();
					break;
				case 3:
					System.out.println("exiting employee");
					break;
				default:
					System.out.println("No valid case, please try again");
			}
			
		} while (choice != 3);
	}
	
//	private String getUsernameInput() {
//		System.out.println("Enter a username that you would like to lookup: ");
//		String input = Menu.sc.nextLine().trim();
//		
//		return input;
//	}
}
