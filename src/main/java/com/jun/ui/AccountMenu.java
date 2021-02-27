package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.CardNotFoundException;
import com.jun.services.CardService;
import com.jun.services.TransactionService;

public class AccountMenu implements Menu{
	
	private String id;
	public CardService cardService;
	public TransactionService transactionService;
	public AccountMenu(String id) {
		this.id = id;
		this.cardService = new CardService();
	}
	
	public void display() {
		int choice = 0;
		double balance = 10000.00;
		try {
			balance = this.cardService.getBalanceByCardNum(this.id);
		} catch (SQLException | CardNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		//we need a service here to get balance for the current account
		do {
			System.out.println("Account Menu for : " + this.id);
			System.out.println("Current Balance: " + balance);
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
					TransactionMenu dtm = new TransactionMenu(this.id, "Deposit");
					dtm.display();
					break;
				case 2:
					System.out.println("Withdraw menu");
					TransactionMenu wtm = new TransactionMenu(this.id, "Withdraw");
					wtm.display();
					break;
				case 3: 
					System.out.println("Transfer menu");
					break;
				case 4: 
					break;
				default:
					System.out.println("Invalid option. Please choose again");
					break;
			}
			
		} while (choice != 4);
	}
}
