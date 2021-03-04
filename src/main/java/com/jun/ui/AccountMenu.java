package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.AccountNotFoundException;
import com.jun.exceptions.UserNotFoundException;
import com.jun.services.AccountService;
import com.jun.services.CustomerService;
import com.jun.services.TransactionService;

public class AccountMenu implements Menu{
	
	public String accountId;
	public int userId;
	public AccountService accountService;
	public TransactionService transactionService;
	public CustomerService customerService;
	public AccountMenu(String accountId, int userId) {
		this.userId = userId;
		this.accountId = accountId;
		this.accountService = new AccountService();
		this.customerService = new CustomerService();
	}
	
	public void display() {
		int choice = 0;
		double balance = 0;
		double custBal = 0;
		try {
			balance = this.accountService.getAccountInfo(accountId, userId).getBalance();
		} catch (SQLException | AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}
	
		try {
			custBal = customerService.getCustomerById(userId).getBalance();
		} catch (UserNotFoundException | SQLException ex) {}
		
		
		do {
			System.out.println("=======================Account Menu for: " + accountId + " ==========================");
			System.out.println("||                          Cash on customer: " + custBal + "                            ||");
			System.out.println("||                          Current Balance on account: " + balance + "                  ||");
			System.out.println("||                          Choose an option below:                               ||");
			System.out.println("||                                1.) Deposit                                     ||");
			System.out.println("||                                2.) Withdraw                                    ||");
			System.out.println("||                                3.) Transfer                                    ||");
			System.out.println("||                                4.) Customer Menu                               ||");
			System.out.println("====================================================================================");
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch(NumberFormatException e) {
			}

			switch (choice) {
				case 1: 
					TransactionMenu dtm = new TransactionMenu(accountId, "Deposit", balance, userId);
					dtm.display();
					break;
				case 2:
					TransactionMenu wtm = new TransactionMenu(accountId, "Withdraw", balance, userId);
					wtm.display();
					break;
				case 3: 
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
