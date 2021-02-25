package com.jun.main;

import com.jun.ui.MainMenu;
import com.jun.ui.Menu;

public class Application {
	public static void main(String[] args) {
		Menu mainMenu = new MainMenu();
		mainMenu.display();
		
		Menu.sc.close();

		System.out.println("Application Closing!");
	}

}
