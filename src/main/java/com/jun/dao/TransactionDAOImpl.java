package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO {
	
	@Override
	public Transaction updateBalance(String cardNum, String transactionType, double amount, Connection con) throws SQLException, NumberFormatException, InvalidBalanceException{
		Transaction transaction = null;
		String getBalSql = "SELECT balance FROM bank.account WHERE account_no = ?";
		String updateBalSql = "UPDATE bank.account SET balance = ? WHERE account_no = ?";
		
		PreparedStatement balPS = con.prepareStatement(getBalSql);
		PreparedStatement updatePS = con.prepareStatement(updateBalSql);
		
		balPS.setString(1, cardNum);
		updatePS.setString(2, cardNum);
		ResultSet balRS = balPS.executeQuery();
		double cardBalance = 0; 
		
		if (balRS.next()) {
			cardBalance = balRS.getDouble("balance");
		}
		
		if (transactionType == "Deposit") {
			cardBalance += amount;
			updatePS.setDouble(1, cardBalance);
			transaction = new Transaction(String.valueOf(cardBalance));
		} else if (transactionType == "Withdraw") {
			if (cardBalance - amount > 0) {
				cardBalance -= amount;
				updatePS.setDouble(1, cardBalance);
				transaction = new Transaction(String.valueOf(cardBalance));
			} else {
				return transaction = null;
			}
		}
		updatePS.executeUpdate();
		
		return transaction;
	}
	
	@Override
	public boolean transferBalance(String fromAccount, String toAccount, double fromBalance, double toBalance, double amount, Connection con) throws SQLException {
		String fromSql = "UPDATE bank.account SET balance = ? WHERE account_no = ?";
		String toSql = "UPDATE bank.account SET balance = ? WHERE account_no =?";	
		System.out.println("FROM: " + fromAccount + "bal: " + fromBalance);
		PreparedStatement fromPS = con.prepareStatement(fromSql);
		fromPS.setDouble(1, fromBalance - amount);
		fromPS.setString(2, fromAccount);
		fromPS.executeUpdate();
		
		PreparedStatement toPS = con.prepareStatement(toSql);
		toPS.setDouble(1, toBalance + amount);
		toPS.setString(2, toAccount);
		toPS.executeUpdate();
		
		return true;
	}

	@Override
	public boolean requestTransfer(String fromAccount, String toAccount, double amount, int toAccountId)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


}
