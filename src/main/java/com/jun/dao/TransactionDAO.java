package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.Transaction;

public interface TransactionDAO {
	public Transaction updateBalance(String cardNum, String transactionType, double amount, Connection con) throws SQLException, NumberFormatException, InvalidBalanceException;
}
