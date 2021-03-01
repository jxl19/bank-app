package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.ApplicationNotFoundException;
import com.jun.exceptions.InvalidApplicationException;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.ApplicationReview;
import com.jun.services.ReviewApplicationService;

public class ReviewApplicationMenu implements Menu {

	public ReviewApplicationService reviewApplicationService;

	public ReviewApplicationMenu() {
		this.reviewApplicationService = new ReviewApplicationService();
	}

	public void display() {
		int choice = 0;
		ApplicationReview ar = null;

		do {
			try {
				ar = reviewApplicationService.reviewApplications();
			} catch (ApplicationNotFoundException | SQLException | NullPointerException e) {
				System.out.println(e.getMessage());
			}
			if (ar != null) {
				System.out.println("=== APPLICATION REVIEW ===");
				System.out.println("Customer Name: " + ar.getFirstName() + " " + ar.getLastName());
				System.out.println("Credit Score: " + ar.getCredit());
				System.out.println("Account starting balance: " + ar.getInitialBalance());
				System.out.println("Please review the application");
				System.out.println("1.) Exit");
				System.out.println("2.) Approve");
				System.out.println("3.) Decline");
				try {
					choice = Integer.parseInt(Menu.sc.nextLine());
				} catch (NumberFormatException e) {
				}
			} else {
				System.out.println("There are no more new applications");
				choice = 1;
			}


			switch (choice) {
			case 1:
				break;
			case 2:
				if (ar != null) {
					try {
						reviewApplicationService.approveAccount(ar.getLoginId(), ar.getAppId(), ar.getInitialBalance(), ar.isCheckingAccount());
						System.out.println("Approved application of..");
					} catch (InvalidBalanceException | InvalidApplicationException |SQLException e) {
						System.out.println(e.getMessage());
					}
				} else {
					choice = 1;
					break;
				}
				break;
			case 3:
				if (ar != null) {
					try {
						reviewApplicationService.rejectAccount(ar.getAppId());
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					} 
					System.out.println("declined");
				} else {
					choice = 1;
					break;
				}
			default:
				System.out.println("Invalid choice, try again!");
			}
		} while (choice != 1);

	}
}
