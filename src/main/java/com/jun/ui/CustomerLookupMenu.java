package com.jun.ui;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
			System.out.println("================================== CUSTOMER LOOKUP ==================================");
			System.out.println("||                               1.) Exit                                          ||");
			System.out.println("||                               2.) Check User Accounts                           ||");
			System.out.println("||                               3.) Check User Logs                               ||");
			System.out.println("=====================================================================================");
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
					System.out.println("=================================== USER LOGS =======================================");
					try {
						getCustLogs();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
					System.out.println("|| 1.) Exit                                         ");
					System.out.println("=====================================================================================");
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
			System.out.println("|| " + log.getTransactionTime() + " User id " + log.getUserId() + " " + log.getAction());
		});
	}

	void getCustAccount() throws UserNotFoundException, SQLException {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		
		ArrayList<Account> userAccounts = accountService.getAllUserAccounts(userId);
		System.out.println("============================= BANK ACCOUNTS =========================================");
		System.out.println("||      ACCOUNT TYPE    ||      ACCOUNT NUMBER      ||        ACCOUNT BALANCE      ||");
		System.out.println("=====================================================================================");
		userAccounts.forEach(account -> {
			String accountType = account.isCheckingAccount() ? "Checkings" : "Savings  ";
			System.out.println("||       " + accountType + "      ||     " + account.getAccountNum() + "     ||            " + df.format(account.getBalance()) + "        ||");
		});
		System.out.println("=====================================================================================");
	}
}
