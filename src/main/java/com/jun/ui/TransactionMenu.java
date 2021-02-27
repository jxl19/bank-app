package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.CardNotFoundException;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.services.CardService;
import com.jun.services.TransactionService;

public class TransactionMenu implements Menu {

	public String cardNum;
	public String transactionType;
	public TransactionService transactionService;
	public CardService cardService;
	
	public TransactionMenu(String cardNum, String transactionType) {
		this.cardNum = cardNum;
		this.transactionType = transactionType;
		this.transactionService = new TransactionService();
		this.cardService = new CardService();
	}

	@Override
	public void display() {

		int choice = 0;
		
		do {
			System.out.println("1.) Exit");
			System.out.println("Please input the amount to " + transactionType.toLowerCase());
			String amount = Menu.sc.nextLine().toString();
			
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
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (NumberFormatException e) {
						System.out.println("Invalid input, please try again!");
					} catch (InvalidBalanceException e) {
						e.printStackTrace();
					}
				}
				break;
		} while (choice != 1);
	}

}
