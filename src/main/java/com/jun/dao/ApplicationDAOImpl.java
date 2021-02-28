package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;

public class ApplicationDAOImpl implements ApplicationDAO {
	
	@Override
	public double applyForNewBankAccount(int loginId, double initialBalance, Connection con) throws SQLException, InvalidBalanceException {
		double userBalance = 0;
		String firstName = "";
		String lastName = "";
		int creditScore = 0;
		
		String applicationQuery = "INSERT INTO bank.pending_applications (login_id, first_name, last_name, credit, initial_balance) VALUES (?,?,?,?,?)";
		String checkUserBalanceQuery = "SELECT * FROM bank.customer WHERE login_id = ?";
		
		PreparedStatement userBalPS = con.prepareStatement(checkUserBalanceQuery);
		userBalPS.setInt(1, loginId);
		System.out.println(loginId + "loginid");
		ResultSet rs = userBalPS.executeQuery();
		if (rs.next()) {
			userBalance = rs.getDouble("cash");
			firstName = rs.getString("first_name");
			lastName = rs.getString("last_name");
			creditScore = rs.getInt("credit");
		}
		
		if (userBalance < initialBalance) {
			return userBalance;
		} else {
			PreparedStatement appPS = con.prepareStatement(applicationQuery);
			
			appPS.setInt(1, loginId);
			appPS.setString(2, firstName);
			appPS.setString(3, lastName);
			appPS.setInt(4, creditScore);
			appPS.setDouble(5, initialBalance);
			
			//we will need to return false if... initial balance is more than what the person has..
			
			appPS.executeUpdate();
			
		}
		return initialBalance;
	}

}
