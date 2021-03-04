package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.services.ApplicationService;

public class AccountApplicationMenu implements Menu {

	public String firstName;
	public String lastName;
	public int creditScore;
	public double initialBalance;
	public boolean isCheckingAccount;
	public ApplicationService applicationService;

	public AccountApplicationMenu(boolean isCheckingAccount) {
		this.applicationService = new ApplicationService();
		this.isCheckingAccount = isCheckingAccount;
	}

	public void display() {
		System.out.println("========================== APPLICATION FOR ACCOUNT ===============================");
		int choice = 0;
		double amount = 0;

		a : do {
		System.out.println("||                              1.) Exit                                        ||");
		System.out.println("||        Please enter your starting balance for this account:                  ||");
		System.out.println("==================================================================================");
			try {
				amount = Double.parseDouble(Menu.sc.nextLine());
			} catch (NumberFormatException e) {};
			
			if (amount == 1) {
				choice = 1;
			}
			switch (choice) {
			case 1:
				break;
			default:
				try {
					boolean application = applicationService.applyForNewAccount(MainMenu.loginId, amount,
							isCheckingAccount);
					if (application) {
						System.out.println("Your application has been recieved");
						break a;
					}
				} catch (SQLException | InvalidBalanceException e) {
					System.out.println(e.getMessage());
				}
				break;
			}
		} while (choice != 1);
	}

}
