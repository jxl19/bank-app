package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.CardNotFoundException;
import com.jun.services.AccountService;
import com.jun.services.TransactionService;

public class AccountMenu implements Menu{
	
	public String accountId;
	public int userId;
	public AccountService cardService;
	public TransactionService transactionService;
	public AccountMenu(String accountId, int userId) {
		this.userId = userId;
		this.accountId = accountId;
		this.cardService = new AccountService();
	}
	
	public void display() {
		int choice = 0;
		double balance = 0;
		try {
			balance = this.cardService.getAccountInfo(accountId, userId).getBalance();
		} catch (SQLException | CardNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		do {
			System.out.println("Account Menu for : " + accountId);
			System.out.println("Current Balance: " + balance);
			System.out.println("Choose an option below:");
			System.out.println("1.) Deposit");
			System.out.println("2.) Withdraw");
			System.out.println("3.) Transfer");
			System.out.println("4.) Customer Menu");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch(NumberFormatException e) {
			}
			
			System.out.println("user id inside accme" + userId);
			switch (choice) {
				case 1: 
					System.out.println("Deposit menu");
					TransactionMenu dtm = new TransactionMenu(accountId, "Deposit", balance, userId);
					dtm.display();
					break;
				case 2:
					System.out.println("Withdraw menu");
					TransactionMenu wtm = new TransactionMenu(accountId, "Withdraw", balance, userId);
					wtm.display();
					break;
				case 3: 
					System.out.println("Transfer menu");
					TransactionMenu ttm = new TransactionMenu(accountId, "Transfer", balance, userId);
					ttm.display();
					break;
				case 4: 
					CustomerMenu cm = new CustomerMenu(userId);
					cm.display();
					break;
				default:
					System.out.println("Invalid option. Please choose again");
					break;
			}
			
		} while (choice != 4);
	}
}
