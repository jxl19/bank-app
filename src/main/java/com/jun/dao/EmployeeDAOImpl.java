package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import com.jun.exceptions.InvalidApplicationException;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.ApplicationReview;

public class EmployeeDAOImpl implements EmployeeDAO {

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
			boolean isCheckingAccount = rs.getBoolean("is_checking_account");
			
			applicationReview = new ApplicationReview(appId, loginId, firstName, lastName, credit, initBalance, isCheckingAccount); 
		}
		System.out.println("appreview!!" + applicationReview);
		return applicationReview;
	}

	@Override
	public boolean approveApplication(int loginId, int appId, double initialBalance, boolean isCheckingAccount, Connection con) throws InvalidBalanceException, SQLException {
		
		
		int firstDigit = ThreadLocalRandom.current().nextInt(3, 6 + 1);
		StringBuilder accountNumber = new StringBuilder();
		accountNumber.append(firstDigit);
		int i = 1;
		while (i <= 15) {
			accountNumber.append(ThreadLocalRandom.current().nextInt(0, 9 + 1));
			i++;
		}
		System.out.println(accountNumber);
		String updateAppSql = "UPDATE bank.pending_applications SET pending = FALSE, approved= TRUE WHERE app_id = ?";
		String createAccSql = "INSERT INTO bank.account (account_no, login_id, balance, is_checking_account) VALUES (?,?,?,?)";
		PreparedStatement updatePS = con.prepareStatement(updateAppSql);
		updatePS.setInt(1, appId);
		
		updatePS.executeUpdate();

		PreparedStatement createPS = con.prepareStatement(createAccSql);
		createPS.setString(1, accountNumber.toString());
		createPS.setInt(2, loginId);
		createPS.setDouble(3, initialBalance);
		createPS.setBoolean(4, isCheckingAccount);
		
		createPS.executeUpdate();
		return true;
	}

	@Override
	public boolean rejectAccount(int appId, Connection con) throws SQLException {
		String sql = "UPDATE bank.pending_applications SET pending = FALSE, approved = FALSE WHERE app_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, appId);
		ps.executeUpdate();
		return true;
	}

	@Override
	public boolean checkIfValidApplication(int appId, Connection con) throws InvalidApplicationException, SQLException {
		boolean valid = false;
		String sql = "SELECT pending FROM bank.pending_applications WHERE app_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, appId);
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			valid = rs.getBoolean("pending");
		}
		return valid;
	}

}
