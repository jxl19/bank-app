package com.jun.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jun.exceptions.CardNotFoundException;
import com.jun.exceptions.UserNotFoundException;
import com.jun.model.Account;
import com.jun.model.TransactionLogs;
import com.jun.services.AccountService;
import com.jun.services.CustomerService;
import com.jun.services.EmployeeService;

public class CustomerLookupMenu implements Menu {

	public int userId;
	public AccountService accountService;
	public CustomerService customerService;
	public EmployeeService employeeService;

	public CustomerLookupMenu(int userId) {
		this.accountService = new AccountService();
		this.customerService = new CustomerService();
		this.employeeService = new EmployeeService();
		this.userId = userId;
	}

	@Override
	public void display() {
		int choice = 0;
		do {
			System.out.println("===CUSTOMER INFORMATION MENU===");
			System.out.println("1.) Exit");
			System.out.println("2.) Check User Accounts");
			System.out.println("3.) Check User Logs");
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {
			}
			switch (choice) {
			case 1:
				break;
			case 2:
				int sel = 0;
				do {
					System.out.println("=== BANK ACCOUNTS ===");
					try {
						getCustAccount();
					} catch (SQLException | UserNotFoundException e) {
						System.out.println(e.getMessage());
					}
					System.out.println("1.) Exit");
					try {
						sel = Integer.parseInt(Menu.sc.nextLine());
					} catch (NumberFormatException e) {
					}
				} while (sel != 1);
				break;
			case 3:
				int logChoice = 0;
				do {
					System.out.println("=== USER LOGS ===");
					try {
						getCustLogs();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
					System.out.println("1.) Exit");
					try {
						logChoice = Integer.parseInt(Menu.sc.nextLine());
					} catch (NumberFormatException e) {
					}
				} while (logChoice != 1);
				break;
			}
		} while (choice != 1);
	}
	
	void getCustLogs() throws SQLException {
		ArrayList<TransactionLogs> at = employeeService.getTransactions(userId);
		at.forEach(log -> {
			System.out.println(log.getTransactionTime() + " User id " + log.getUserId() + " " + log.getAction());
		});
	}

	void getCustAccount() throws UserNotFoundException, SQLException {
		List<String> ids = new ArrayList<>();
		//TODO: update accountservice to just return all accounts
		ids = customerService.getCustomerCardNumber(userId);
		Account acc = null;
		String accountType = "";
		StringBuilder sb = new StringBuilder();
		if (ids.size() > 0) {
			for (int i = 0; i <= ids.size(); i++) {
				if (i < ids.size()) {
					try {
						acc = accountService.getAccountInfo(ids.get(i), userId);
						accountType = acc.isCheckingAccount() ? "Checkings" : "Savings  ";
					} catch (SQLException | CardNotFoundException e) {
						System.out.println(e.getMessage());
					}
					sb.append(accountType + "  -  " + ids.get(i) + "  -  Account Balance: " + acc.getBalance());
				} else {

				}
				sb.append(System.getProperty("line.separator"));
			}
			System.out.println(sb);
		}
	}
}