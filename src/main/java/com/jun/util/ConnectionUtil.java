package com.jun.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class ConnectionUtil {
	public static Connection getConnection() throws SQLException{
		DriverManager.registerDriver(new Driver()); 
		
		String url = System.getenv("db_url");
		String username = System.getenv("db_username");
		String password = System.getenv("db_password");
		Connection con = DriverManager.getConnection(url, username, password);
		return con;
	}
}
