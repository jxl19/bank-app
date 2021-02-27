package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO {
	
	
	@Override
	public Transaction updateBalance(String cardNum, String transactionType, String amount, Connection con) throws SQLException, NumberFormatException, InvalidBalanceException{
		Transaction transaction = null;
		String getBalSql = "SELECT balance FROM bank.card WHERE card_no = ?";
		String updateBalSql = "UPDATE bank.card SET balance = ? WHERE card_no = ?";
		
		PreparedStatement balPS = con.prepareStatement(getBalSql);
		PreparedStatement updatePS = con.prepareStatement(updateBalSql);
		
		balPS.setString(1, cardNum);
		updatePS.setString(2, cardNum);
		ResultSet balRS = balPS.executeQuery();
		double cardBalance = 0; 
		
		if (balRS.next()) {
			cardBalance = balRS.getDouble("balance");
		}
		System.out.println("before");
		double transactionAmount = Double.parseDouble(amount);
		if (transactionType == "Deposit") {
			cardBalance += transactionAmount;
			updatePS.setDouble(1, cardBalance);
			transaction = new Transaction(String.valueOf(cardBalance));
		} else if (transactionType == "Withdraw") {
			if (cardBalance - transactionAmount > 0) {
				cardBalance -= transactionAmount;
				updatePS.setDouble(1, cardBalance);
				transaction = new Transaction(String.valueOf(cardBalance));
			} else {
				throw new InvalidBalanceException("Not enough balance.. try another amouint");
			}
		}
		System.out.println("after");
		updatePS.executeUpdate();
		
//		System.out.println("New balance is :" + balRS.getDouble("balance"));
		
		return transaction;
	}

}
