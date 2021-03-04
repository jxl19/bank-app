package com.jun.ui;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.jun.exceptions.UserNotFoundException;
import com.jun.model.Account;
import com.jun.services.AccountService;
import com.jun.services.CustomerService;

public class CustomerMenu implements Menu{
	
	public CustomerService customerService;
	public AccountService accountService;
	public int userId;
	public CustomerMenu(int userId) {
		this.customerService = new CustomerService();
		this.accountService = new AccountService();
		this.userId = userId;
	}
	
	public void display() {
		
		int choice = 0;
		
		do {
			System.out.println("============================ Customer Menu ====================================");
			System.out.println("||                   Please select an option below:                          ||");
			System.out.println("||                      1.) Select Bank Account                              ||");
			System.out.println("||                      2.) Pending Transfers                                ||");
			System.out.println("||                      3.) Apply for new Checkings account                  ||");
			System.out.println("||                      4.) Apply for new Savings account                    ||");
			System.out.println("||                      5.) Log Out                                          ||");
			System.out.println("===============================================================================");
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			switch (choice) {
				case 1: 
					try {
						getCustAccount(userId);
					} catch (UserNotFoundException | SQLException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2: 
					System.out.println("Checking transfers");
					PendingTransferMenu ptm = new PendingTransferMenu();
					ptm.display();
					break;
				case 3: 
					AccountApplicationMenu caam = new AccountApplicationMenu(true);
					caam.display();
					break;
				case 4: 
					AccountApplicationMenu saam = new AccountApplicationMenu(false);
					saam.display();
					break;
				case 5:
					System.out.println("Logging out");
					MainMenu mm = new MainMenu();
					mm.display();
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while (choice != 5);
	}
	
	void getCustAccount(int id) throws UserNotFoundException, SQLException {
		
		int choice = 0;
		
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		
		ArrayList<Account> userAccounts = accountService.getAllUserAccounts(userId);
		int numOfAccounts = userAccounts.size();
		if(numOfAccounts == 0) {
			CustomerMenu cm = new CustomerMenu(userId);
			System.out.println("There are no bank accounts");
			cm.display();
		}

		System.out.println("============================= BANK ACCOUNTS =========================================");
		System.out.println("||    ACCOUNT TYPE    ||      ACCOUNT NUMBER      ||        ACCOUNT BALANCE        ||");
		System.out.println("=====================================================================================");
		userAccounts.forEach(withCounter((i, account)-> {
			String accountType = account.isCheckingAccount() ? "Checkings" : "Savings  ";
			System.out.println(i + ".)     " + accountType + "      ||     " + account.getAccountNum() + "     ||            " + df.format(account.getBalance()) + "         ||");
		}));
		System.out.println("=====================================================================================");
		try { 
			choice = Integer.parseInt(Menu.sc.nextLine());
		} catch (NumberFormatException e) {} 
		

		
		if (choice <= numOfAccounts & choice != 0) {
			String acc = userAccounts.get(choice - 1).getAccountNum();
			AccountMenu am = new AccountMenu(acc, userId);
			am.display();
		} else {
			System.out.println("invalid");
			this.getCustAccount(id);
		}

	}
	
	//create counter in foreach
	private static <T>Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
	    AtomicInteger counter = new AtomicInteger(1);
	    return item -> consumer.accept(counter.getAndIncrement(), item);
	}
}