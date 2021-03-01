package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jun.model.Account;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public Account getCardInfo(String accountNum, Connection con) throws SQLException {
		Account card = null;
		String sql = "SELECT * FROM bank.account WHERE account_no = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, accountNum);
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			int accountId = rs.getInt("login_id");
			double balance = rs.getDouble("balance");
			boolean isCheckingAccount = rs.getBoolean("is_checking_account");
			card = new Account(accountNum, balance, accountId, isCheckingAccount);
		}
	
		return card;
	}

}
