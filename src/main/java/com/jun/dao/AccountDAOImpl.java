package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jun.model.Account;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public Account getAccountInfo(String accountNum, Connection con) throws SQLException {
		Account account = null;
		String sql = "SELECT * FROM bank.account WHERE account_no = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, accountNum);
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			int userId = rs.getInt("login_id");
			double balance = rs.getDouble("balance");
			boolean isCheckingAccount = rs.getBoolean("is_checking_account");
			account = new Account(accountNum, balance, userId, isCheckingAccount);
		}
	
		return account;
	}

	@Override
	public ArrayList<Account> getAllUserAccounts(int userId, Connection con) throws SQLException {
		ArrayList<Account> userAccounts = new ArrayList<>();
		String sql = "SELECT * FROM bank.account WHERE login_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			String accountNum = rs.getString("account_no");
			int loginId = rs.getInt("login_id");
			double balance = rs.getDouble("balance");
			boolean isCheckingAccount = rs.getBoolean("is_checking_account");
			userAccounts.add(new Account(accountNum, balance, loginId, isCheckingAccount));
		}
		return userAccounts;
	}

}
