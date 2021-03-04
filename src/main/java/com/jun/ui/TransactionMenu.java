package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.AccountNotFoundException;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.exceptions.InvalidTransferRequestException;
import com.jun.exceptions.UserNotFoundException;
import com.jun.services.AccountService;
import com.jun.services.TransactionService;

public class TransactionMenu implements Menu {

	public String cardNum;
	public String transactionType;
	public double balance;
	public int userId;
	public TransactionService transactionService;
	public AccountService cardService;

	public TransactionMenu(String cardNum, String transactionType, double balance, int userId) {
		this.cardNum = cardNum;
		this.transactionType = transactionType;
		this.balance = balance;
		this.userId = userId;
		this.transactionService = new TransactionService();
		this.cardService = new AccountService();
	}

	@Override
	public void display() {

		int choice = 0;

		do {
			System.out.println("===================================== " + transactionType.toUpperCase() + " MENU ==================================");
			System.out.println("||                   Please enter an amount to " + transactionType.toLowerCase() +" or go back                  ||");
			System.out.println("||                                     1.) Exit                                    ||");
			System.out.println("||                             Current balance is: " + String.valueOf(balance) + "                         ||");
			System.out.println("||           		           Amount to " + transactionType.toLowerCase() + "                               ||");
			System.out.println("=====================================================================================");
			double amount = Double.parseDouble(Menu.sc.nextLine());
			if ((int)amount == 1) {
				choice = 1;
			}
			switch (choice) {
			case 1:
				break;
			default:
				if (transactionType != "Transfer") {
					try {
						transactionService.transferBalance(cardNum, transactionType, amount, userId);
						try {
							System.out.println("Transaction successful .The updated balance is : "
									+ cardService.getAccountInfo(cardNum, userId).getBalance());
							AccountMenu am = new AccountMenu(cardNum, userId);
							am.display();
						} catch (AccountNotFoundException e) {
							e.printStackTrace();
						}
					} catch (SQLException | NumberFormatException | InvalidBalanceException | UserNotFoundException e) {
						System.out.println(e.getMessage());
					}
				} else {
					try {
						System.out.println("Please enter account number that you want to transfer to");
						String toAcc = Menu.sc.nextLine();
						String transfer = transactionService.transferBalanceToAccount(userId, toAcc, cardNum, amount);
						System.out.println(transfer);
						AccountMenu am = new AccountMenu(cardNum, userId);
						am.display();
					} catch (SQLException | InvalidBalanceException | InvalidTransferRequestException
							| UserNotFoundException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			break;
		} while (choice != 1);
	}

}
