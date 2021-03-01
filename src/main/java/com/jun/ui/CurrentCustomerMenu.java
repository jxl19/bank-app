package com.jun.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jun.exceptions.CardNotFoundException;
import com.jun.exceptions.UserNotFoundException;
import com.jun.services.AccountService;
import com.jun.services.CustomerService;

public class CurrentCustomerMenu implements Menu {
	
	public int id;
	public AccountService cardService;
	public CustomerService customerService;
	
	public CurrentCustomerMenu() {
		this.cardService = new AccountService();
		this.customerService = new CustomerService();
	}

	@Override
	public void display() {
		try {
			getCustAccount(id);
		} catch (SQLException | UserNotFoundException e) {
			System.out.println(e.getMessage());
		} 
	}

	void getCustAccount(int id) throws UserNotFoundException, SQLException {
		List<String> ids = new ArrayList<>();
		ids = customerService.getCustomerCardNumber(id);
		
		int choice = 0;
		double bal = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("=== BANK ACCOUNTS===");
		sb.append(System.getProperty("line.separator"));
		if (ids.size() > 0) {
			sb.append("Select a bank account below to check logs");
			sb.append(System.getProperty("line.separator"));
			for (int i = 0; i <= ids.size(); i++) {
				if (i < ids.size()) {
					sb.append(i+1 + ".) " + ids.get(i));
					try {
						bal = cardService.getBalanceByCardNum(ids.get(i));
					} catch (SQLException | CardNotFoundException e) {
						System.out.println(e.getMessage());
					} 
					sb.append(" - Account Balance: " + bal);
				} else {
					sb.append(i+1 + ".) Check all logs of customer");
					sb.append(System.getProperty("line.separator"));
					sb.append(i+2 + ".) Exit");
				}
				sb.append(System.getProperty("line.separator"));
			}
			System.out.println(sb);
			
			try { 
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {} 
			
			//exit menu
			if (choice - 1 == ids.size()) {
				sb.append(System.getProperty("line.separator"));
				System.out.println("checking all logs");
			}
			if (choice - 2 == ids.size()) {
				ReviewCustomerMenu rcm = new ReviewCustomerMenu();
				System.out.println("Exiting..");
				rcm.display();
			}
			
			if (choice <= ids.size() & choice != 0) {
				String acc = ids.get(choice - 1);
				sb.append(System.getProperty("line.separator"));
				sb.append(System.getProperty("line.separator"));
				System.out.println("Reviewing logs! of " + acc);

			} else {
				System.out.println("invalid");
				this.getCustAccount(id);
			}
		} else {
			System.out.println("This customer has no bank accounts. Exiting.");
		}

	}
}
