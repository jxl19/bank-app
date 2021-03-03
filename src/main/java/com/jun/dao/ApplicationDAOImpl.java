package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;

public class ApplicationDAOImpl implements ApplicationDAO {

	@Override
	public void applyForNewBankAccount(int loginId, double initialBalance, boolean isCheckingAccount, Connection con)
			throws SQLException, InvalidBalanceException {
		String firstName = "";
		String lastName = "";
		int creditScore = 0;

		String applicationQuery = "INSERT INTO bank.pending_applications (login_id, first_name, last_name, credit, initial_balance, is_checking_account) VALUES (?,?,?,?,?,?)";
		String userInfoQuery = "SELECT first_name, last_name, credit FROM bank.users JOIN bank.customer ON bank.users.login_id = bank.customer.login_id WHERE bank.customer.login_id = ?;";

		PreparedStatement userBalPS = con.prepareStatement(userInfoQuery);
		userBalPS.setInt(1, loginId);
		ResultSet rs = userBalPS.executeQuery();
		if (rs.next()) {
			firstName = rs.getString("first_name");
			lastName = rs.getString("last_name");
			creditScore = rs.getInt("credit");
		}

		PreparedStatement appPS = con.prepareStatement(applicationQuery);

		appPS.setInt(1, loginId);
		appPS.setString(2, firstName);
		appPS.setString(3, lastName);
		appPS.setInt(4, creditScore);
		appPS.setDouble(5, initialBalance);
		appPS.setBoolean(6, isCheckingAccount);

		appPS.executeUpdate();

	}

}
