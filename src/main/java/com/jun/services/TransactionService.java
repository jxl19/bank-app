package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.TransactionDAO;
import com.jun.dao.TransactionDAOImpl;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.Transaction;
import com.jun.util.ConnectionUtil;

public class TransactionService {

	public TransactionDAO transactionDAO;
	public TransactionService() {
		this.transactionDAO = new TransactionDAOImpl();
	}
	
	public Transaction updateBalance(String cardNum, String transactionType, double amount) throws SQLException, NumberFormatException, InvalidBalanceException {
		if (amount < 0) {
			//CHANGE OUR INPUT TO DOUBLE INSTEAD OF STRING LATER????
			throw new InvalidBalanceException("You cannot input a negative balance!");
		}
		try (Connection con = ConnectionUtil.getConnection()) {
			Transaction transaction = transactionDAO.updateBalance(cardNum, transactionType, amount, con);
			if (transaction == null) {
				throw new InvalidBalanceException("There is not enough balance to withdraw");
			}
			return transaction;
		}
	}

}
