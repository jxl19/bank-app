package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.Transaction;

public interface TransactionDAO {
	public Transaction updateBalance(String cardNum, String transactionType, double amount, Connection con) throws SQLException, NumberFormatException, InvalidBalanceException;
	public boolean transferBalance(String fromAccount, String toAccount,  double fromBalance, double toBalance, double amount, Connection con) throws SQLException;
	public boolean requestTransfer(String fromAccount, String toAccount, double amount, int toAccountId) throws SQLException;
}
