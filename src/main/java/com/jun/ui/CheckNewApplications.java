package com.jun.ui;

import java.util.Arrays;

public class CheckNewApplications implements Menu {
	
	public CheckNewApplications() {
		
	}
	
	public void display() {
		int choice = 0;
		
		//while loop for checking newly applied accounts which will display fn, ln, credit score
		//menu will also consist of exit
		String[][] users = {{"Jun","Lei","555"}, {"Joey", "Chen", "777"}, {"King", "Wong", "888"}};
			for (int i = 0; i < users.length; i++) {
				System.out.println("iteration: " + i);
					System.out.println(Arrays.toString(users[i]));
					System.out.println("1.) Approve");
					System.out.println("2.) Decline");
					System.out.println("3.) Exit");
					try {
						choice = Integer.parseInt(Menu.sc.nextLine());
					} catch (NumberFormatException e) {}

					if (choice == 1) {
						System.out.println("acc");
					} else if (choice == 2) {
						System.out.println("dec");
					} else if (choice == 3) {
						System.out.println("exiting");
						break;
					} else {
						System.out.println("try again");
					}

			}

	}
}
