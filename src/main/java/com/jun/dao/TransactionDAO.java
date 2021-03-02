package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.PendingTransfer;
import com.jun.model.Transaction;

public interface TransactionDAO {
	public Transaction updateBalance(String cardNum, String transactionType, double amount, Connection con) throws SQLException, NumberFormatException, InvalidBalanceException;
	public boolean transferBalance(String fromAccount, String toAccount,  double fromBalance, double toBalance, double amount, Connection con) throws SQLException;
	public boolean requestTransfer(String fromAccount, String toAccount, double amount, int toAccountId, Connection con) throws SQLException;
	public ArrayList<PendingTransfer> checkPendingTransfers(int accountId, Connection con) throws SQLException;
	public PendingTransfer approveTransfer(int transferId, Connection con) throws SQLException;
	//approvetransfer
	//declinetransfer
}
