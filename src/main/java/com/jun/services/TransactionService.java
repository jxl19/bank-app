package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jun.dao.AccountDAO;
import com.jun.dao.AccountDAOImpl;
import com.jun.dao.TransactionDAO;
import com.jun.dao.TransactionDAOImpl;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.Account;
import com.jun.model.PendingTransfer;
import com.jun.model.Transaction;
import com.jun.util.ConnectionUtil;

public class TransactionService {

	public TransactionDAO transactionDAO;
	public AccountDAO cardDAO;
	
	public TransactionService() {
		this.transactionDAO = new TransactionDAOImpl();
		this.cardDAO = new AccountDAOImpl();
	}
	
	public Transaction updateBalance(String cardNum, String transactionType, double amount) throws SQLException, NumberFormatException, InvalidBalanceException {
		if (amount < 0) {
			
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
	
	public String transferBalanceToAccount(int id, String toAccount, String fromAccount, double amount) throws SQLException, InvalidBalanceException {
		try (Connection con = ConnectionUtil.getConnection()) {
			String ret = "";
			Account fromAcc = cardDAO.getCardInfo(fromAccount, con);
			Account toAcc = cardDAO.getCardInfo(toAccount, con);
			
			double fromBal = fromAcc.getBalance();
			double toBal = toAcc.getBalance();
			int toID = toAcc.getAccountId();
			if (fromBal < amount) {
				throw new InvalidBalanceException("You do not have enough in your account to make this transfer");
			} else {
				if (id == toID) {
					transactionDAO.transferBalance(fromAccount, toAccount, fromBal, toBal, amount, con);
					ret = "The transfer is completed";
				} else {
					transactionDAO.requestTransfer(fromAccount, toAccount, amount, toID, con);
					ret = "Your transfer is pending";
				}
					
			}
			return ret;
		}
	}
	
	public ArrayList<PendingTransfer> checkPendingTransfer(int accountId) throws SQLException {
		try(Connection con = ConnectionUtil.getConnection()) {
			ArrayList<PendingTransfer> pendingTransfer = new ArrayList<>();
			pendingTransfer = transactionDAO.checkPendingTransfers(accountId, con);
			return pendingTransfer;
		}
	}

}
