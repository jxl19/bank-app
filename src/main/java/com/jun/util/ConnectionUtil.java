package com.jun.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.postgresql.Driver;

public class ConnectionUtil {
	
	private static Logger log = Logger.getLogger(ConnectionUtil.class);
	
	public static Connection getConnection() throws SQLException{
		try {
			DriverManager.registerDriver(new Driver()); 
			
			String url = System.getenv("db_url");
			String username = System.getenv("db_username");
			String password = System.getenv("db_password");
			Connection con = DriverManager.getConnection(url, username, password);
			return con;
		} catch (SQLException e) {
			log.error("Unable to establish a connection! Exception message: " + e.getMessage());
			throw new RuntimeException("Unable to establish connection");
		}
	}
}
