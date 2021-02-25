package com.jun.ui;

//PROBABLY DELETE? OR THIS IS FOR EMPLOYEES TO USE TO CHECK USER ACCOUNT
public class CheckUserAccount implements Menu{
	
	public CheckUserAccount() {
		
	}
	
	public void display() {
		
		int choice = 0;
		
		do {
			//we can use the system we made in usermenu getcustaccount.. abstract class for this? 
			System.out.println("Enter account number:");
			System.out.println("Enter 1 to exit");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			System.out.println(choice);
			//logic to find user account
			
		} while(choice != 1);
	}
	
}
