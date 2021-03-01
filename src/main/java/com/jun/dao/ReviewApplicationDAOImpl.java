package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.ApplicationReview;

public class ReviewApplicationDAOImpl implements ReviewApplicationDAO {

	@Override
	public ApplicationReview reviewCustomerApplication(Connection con) throws SQLException{
		ApplicationReview applicationReview = null;
		String sql = "SELECT * FROM bank.pending_applications WHERE pending = true LIMIT 1";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			int appId = rs.getInt("app_id");
			int loginId = rs.getInt("login_id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			int credit = rs.getInt("credit");
			double initBalance = rs.getDouble("initial_balance");
			
			applicationReview = new ApplicationReview(appId, loginId, firstName, lastName, credit, initBalance); 
		}
		return applicationReview;
	}

	@Override
	public boolean approveApplication(int loginId, int appId, double initialBalance, Connection con) throws InvalidBalanceException, SQLException {
		//we will need login id and app id to update those 2
		//we will need multiple queries - one to get persons balance and another to update blanace and create the card and then create the account
		//set autocommit to false somewhere
		
		int firstDigit = ThreadLocalRandom.current().nextInt(3, 6 + 1);
		StringBuilder accountNumber = new StringBuilder();
		accountNumber.append(firstDigit);
		int i = 1;
		while (i <= 15) {
			accountNumber.append(ThreadLocalRandom.current().nextInt(0, 9 + 1));
			i++;
		}
		System.out.println(accountNumber);
		String updateAppSql = "UPDATE bank.pending_applications SET pending = FALSE WHERE app_id = ?";
		String createAccSql = "INSERT INTO bank.card (card_no, login_id, balance) VALUES (?,?,?)";
		PreparedStatement updatePS = con.prepareStatement(updateAppSql);
		updatePS.setInt(1, appId);
		
		updatePS.executeUpdate();

		PreparedStatement createPS = con.prepareStatement(createAccSql);
		createPS.setString(1, accountNumber.toString());
		createPS.setInt(2, loginId);
		createPS.setDouble(3, initialBalance);
		
		createPS.executeUpdate();
		return true;
	}

	@Override
	public boolean declineApplication(int loginId, int appId, double intialBalance, Connection con) throws InvalidBalanceException, SQLException {
		
		return true;
	}

}
