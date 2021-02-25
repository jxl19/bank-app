package com.jun.ui;

public class UserMenu implements Menu{
	
	public UserMenu() {
		
	}
	
	public void display() {
		
		int choice = 0;
		
		do {
			System.out.println("=== USER MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Select Customer Account");
			System.out.println("2.) Back");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			switch (choice) {
				case 1: 
					getCustAccount();
					break;
				case 2:
					Menu.sc.close();
					break;
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while (choice != 2);
	}
	
	private String getUsernameInput() {
		System.out.println("Enter a username that you would like to lookup: ");
		String input = Menu.sc.nextLine().trim();
		
		return input;
	}
	
	private static void getCustAccount() {
		//OR THIS COULD JUST BE A SELECTION OF ACCOUNTS... EASIER THAN ENTERING IDS
		int[] ids = {7777777,8888888,9999999}; //might need to somehow figure out how to get ids in an array
		int account_choice = 0;
		int ac = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("=== CUSTOMER ACCOUNTS===");
		sb.append(System.getProperty("line.separator"));
		sb.append("Select a customer account below");
		sb.append(System.getProperty("line.separator"));
		for (int i = 0; i <= ids.length; i++) {
			if (i < ids.length) sb.append(i+1 + ".) " + ids[i]);
			else sb.append(i+1 + ".) Exit");
			sb.append(System.getProperty("line.separator"));
		}
		System.out.println(sb);
		try { 
			account_choice = Integer.parseInt(Menu.sc.nextLine());
		} catch (NumberFormatException e) {} 
		
		if (account_choice - 1 == ids.length) {
			UserMenu um = new UserMenu();
			um.display();
		}
		
		try {
			ac = ids[account_choice - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Invalid selection, try again");
			getCustAccount();
		}

		if (ac > 0) {
			System.out.println(ac);
			AccountMenu am = new AccountMenu(ac);
			am.display();
		} 

	}
}
