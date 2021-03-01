package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.ApplicationNotFoundException;
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
		try {
			ar = reviewApplicationService.reviewApplications();
		} catch (ApplicationNotFoundException | SQLException | NullPointerException e) {
			System.out.println(e.getMessage());
		} 
		if (ar == null) {
			choice = 3;
		}
		
		do {
			System.out.println("=== APPLICATION REVIEW ===");
			System.out.println("Customer Name: " + ar.getFirstName() + " " + ar.getLastName());
			System.out.println("Credit Score: " + ar.getCredit());
			System.out.println("Account starting balance: " + ar.getInitialBalance());
			System.out.println("Please review the application");
			System.out.println("1.) Approve");
			System.out.println("2.) Decline");
			System.out.println("3.) Exit");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {}
			
			
			switch (choice) {
			case 1: 
				System.out.println("approved");
				try {
					reviewApplicationService.approveAccount(ar.getLoginId(), ar.getAppId(), ar.getInitialBalance());
					this.display();
				} catch (InvalidBalanceException | SQLException e) {
					System.out.println(e.getMessage());
				} 
				break;
			case 2:
				System.out.println("declined");
				break;
			case 3:
				break;
			default: 
				System.out.println("Invalid choice, try again!");
		}
			
		} while (choice != 3);
		
		
		
		
		//while loop for checking newly applied accounts which will display fn, ln, credit score
		//menu will also consist of exit
		//somewhere during this tranaction we will need to see if customer still has that amount to deposit
		//bc they might have tried to apply for another account and possibly could create a person with a negative cashflow.. wedont want that
		
		//we will then need another service to handle the approval
//		String[][] users = {{"Jun","Lei","555", "10000.00"}, {"Joey", "Chen", "777"}, {"King", "Wong", "888"}};
//			for (int i = 0; i < users.length; i++) {
//				System.out.println("iteration: " + i);
//					System.out.println(Arrays.toString(users[i]));
//					System.out.println("1.) Approve");
//					System.out.println("2.) Decline");
//					System.out.println("3.) Exit");
//					try {
//						choice = Integer.parseInt(Menu.sc.nextLine());
//					} catch (NumberFormatException e) {}
//
//					if (choice == 1) {
//						System.out.println("acc");
//					} else if (choice == 2) {
//						System.out.println("dec");
//					} else if (choice == 3) {
//						System.out.println("exiting");
//						break;
//					} else {
//						System.out.println("try again");
//					}
//
//			}

	}
}
