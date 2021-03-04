package com.jun.ui;

import java.sql.SQLException;

import com.jun.exceptions.ApplicationNotFoundException;
import com.jun.exceptions.InvalidApplicationException;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.ApplicationReview;
import com.jun.services.EmployeeService;

public class ReviewApplicationMenu implements Menu {

	public EmployeeService employeeService;

	public ReviewApplicationMenu() {
		this.employeeService = new EmployeeService();
	}

	public void display() {
		int choice = 0;
		ApplicationReview ar = null;
		int prevId = 0;
		a: do {
			try {
				ar = employeeService.reviewApplications();
			} catch (ApplicationNotFoundException | SQLException e) {
				System.out.println(e.getMessage());
			}
			if (ar == null || ar.getAppId() == prevId) {
				break a;
			}
				System.out.println("=== APPLICATION REVIEW ===");
				System.out.println("Customer Name: " + ar.getFirstName() + " " + ar.getLastName());
				System.out.println("Customer id: " + ar.getLoginId());
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

			switch (choice) {
			case 1:
				break;
			case 2:
					try {
						employeeService.approveAccount(ar.getLoginId(), ar.getAppId(), ar.getInitialBalance(), ar.isCheckingAccount());
					} catch (InvalidBalanceException | InvalidApplicationException |SQLException e) {
						System.out.println(e.getMessage());
					}
				break;
			case 3:
					try {
						employeeService.rejectAccount(ar.getAppId());
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
					break;
			default:
				System.out.println("Invalid choice, try again!");
			}
			prevId = ar.getAppId();
		} while (choice != 1);
	}
}
