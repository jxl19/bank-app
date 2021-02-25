package com.jun.ui;

public class AccountMenu implements Menu{
	
	private int id;

	public AccountMenu(int id) {
		this.id = id;
	}
	
	public void display() {
		int choice = 0;
		
		do {
			System.out.println("Account Menu for : " + this.id);
			System.out.println("Choose an option below:");
			System.out.println("1.) Deposit");
			System.out.println("2.) Withdraw");
			System.out.println("3.) Transfer");
			System.out.println("4.) Exit");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch(NumberFormatException e) {
			}
			
			switch (choice) {
				case 1: 
					System.out.println("Deposit menu");
					break;
				case 2:
					System.out.println("Withdraw menu");
					break;
				case 3: 
					System.out.println("Transfer menu");
					break;
				default:
					System.out.println("Invalid option. Please choose again");
			}
			
		} while (choice != 4);
	}
}
