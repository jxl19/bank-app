package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.CardNotFoundException;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.services.CardService;
import com.jun.services.TransactionService;

public class TransactionMenu implements Menu {

	public String cardNum;
	public String transactionType;
	public double balance;
	public TransactionService transactionService;
	public CardService cardService;
	
	public TransactionMenu(String cardNum, String transactionType, double balance) {
		this.cardNum = cardNum;
		this.transactionType = transactionType;
		this.balance = balance;
		this.transactionService = new TransactionService();
		this.cardService = new CardService();
	}

	@Override
	public void display() {
		
		int choice = 0;
		
		do {
			System.out.println("1.) Exit");
			System.out.println("Current balance is: " + String.valueOf(balance));
			System.out.println("Please input the amount to " + transactionType.toLowerCase());
			double amount = Integer.parseInt(Menu.sc.nextLine());
			switch (choice) {
				case 1: 
					break;
				default: 
					try {
						this.transactionService.updateBalance(cardNum, transactionType, amount);
						try {
							System.out.println("the updated balance is : " + this.cardService.getBalanceByCardNum(cardNum));
							AccountMenu am = new AccountMenu(cardNum);
							am.display();
						} catch (CardNotFoundException e) {
							e.printStackTrace();
						}
					} catch (SQLException | NumberFormatException | InvalidBalanceException e) {
						System.out.println(e.getMessage());
					} 
				}
				break;
		} while (choice != 1);
	}

}
