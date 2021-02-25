package com.jun.ui;

public class MainMenu implements Menu {

	public void display() {
		//some service to grab user type
		boolean isAdmin = false;
		System.out.println("=== Welcome to Bank of Jun! ===");
		
		System.out.println("Please Enter your username: ");
		String un = Menu.sc.nextLine();
		System.out.println("Please Enter your password: ");
		String pw = Menu.sc.nextLine();
		
		System.out.println("username: " + un);
		System.out.println("pw: " + pw);
		
		//LoginService.login(un,pw); 
		System.out.println();
		EmployeeMenu em = new EmployeeMenu();
		UserMenu um = new UserMenu();
		if (isAdmin) {
			em.display();
		} else um.display();
	}
	
}


//int choice = 0;

//TODO: User menu and employee menu should be figured out on its own at login

//do {
//	//maybe this should just be a login..
//	//we can have a flag to tell us if its a user or emp
//
////	System.out.println("=== MAIN MENU ===");
////	System.out.println("Please select an option below: ");
////	System.out.println("1.) Employee Menu");
////	System.out.println("2.) User menu");
////	System.out.println("3.) Exit Application");
//	
//	try {
//		choice = Integer.parseInt(Menu.sc.nextLine());
//	} catch (NumberFormatException e) {
//		
//	}
//	
//	switch (choice) {
//		case 1: 
//			break;
//		case 2:
//			System.out.println("user menu");
//			Menu userMenu = new UserMenu();
//			userMenu.display();
//			break;
//		case 3: 
//			System.out.println("employee menu");
//			Menu employeeMenu = new EmployeeMenu();
//			employeeMenu.display();
//			break;
//		default:
//			System.out.println("Not valid choice.");
//	}
//	
//} while (choice != 3);