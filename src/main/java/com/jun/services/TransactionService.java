package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jun.dao.AccountDAO;
import com.jun.dao.AccountDAOImpl;
import com.jun.dao.LogDAO;
import com.jun.dao.LogDAOImpl;
import com.jun.dao.TransactionDAO;
import com.jun.dao.TransactionDAOImpl;
import com.jun.exceptions.InvalidAccountException;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.exceptions.InvalidTransferRequestException;
import com.jun.model.Account;
import com.jun.model.PendingTransfer;
import com.jun.model.Transaction;
import com.jun.util.ConnectionUtil;

public class TransactionService {

	public TransactionDAO transactionDAO;
	public AccountDAO accountDAO;
	public LogDAO logDAO;
	
	public TransactionService() {
		this.transactionDAO = new TransactionDAOImpl();
		this.accountDAO = new AccountDAOImpl();
		this.logDAO = new LogDAOImpl();
	}
	
	public Transaction transferBalance(String cardNum, String transactionType, double amount, int userId) throws SQLException, NumberFormatException, InvalidBalanceException {
		if (amount < 0) {
			throw new InvalidBalanceException("You cannot input a negative balance!");
		}
		try (Connection con = ConnectionUtil.getConnection()) {
			Transaction transaction = transactionDAO.updateBalance(cardNum, transactionType, amount, con);
			if (transaction == null) {
				throw new InvalidBalanceException("There is not enough balance to withdraw");
			}
			
			logDAO.logUserAction(userId,cardNum +" : "+ transactionType + "ing balance " , con);
			return transaction;
		}
	}
	
	public String transferBalanceToAccount(int userId, String toAccount, String fromAccount, double amount) throws SQLException, InvalidBalanceException, InvalidTransferRequestException, InvalidAccountException {
		if (toAccount == fromAccount) {
			throw new InvalidTransferRequestException("You cannot transfer to yourself!");
		}
		try (Connection con = ConnectionUtil.getConnection()) {
			String ret = "";
			String action = "";
			Account toAcc = accountDAO.getCardInfo(toAccount, con);
			if (toAcc == null) {
				throw new InvalidAccountException("This account this not exist. Please try again.");
			}
			Account fromAcc = accountDAO.getCardInfo(fromAccount, con);
			
			double fromBal = fromAcc.getBalance();
			double toBal = toAcc.getBalance();
			int toID = toAcc.getAccountId();
			if (fromBal < amount) {
				throw new InvalidBalanceException("You do not have enough in your account to make this transfer");
			} else {
				if (userId == toID) {
					transactionDAO.transferBalance(fromAccount, toAccount, fromBal, toBal, amount, con);
					ret = "The transfer is completed";
					action = "successfully transferred from " + fromAccount + " to " + toAccount;
				} else {
					transactionDAO.requestTransfer(fromAccount, toAccount, amount, toID, con);
					ret = "Your transfer is pending";
					action = "requested a transfer";
				}
					
			}
			logDAO.logUserAction(userId, action, con);
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
	
	public String approveTransfer(int transferId, int userId) throws SQLException, InvalidBalanceException{
		try(Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false);
			String ret = "";
			PendingTransfer pendingTransfer = transactionDAO.approveTransfer(transferId, con);

			String fromAccId = pendingTransfer.getFromAccountId();
			String toAccId = pendingTransfer.getToAccountId();
			Account fromAcc = accountDAO.getCardInfo(fromAccId, con);
			Account toAcc = accountDAO.getCardInfo(toAccId, con);
			double fromBal = fromAcc.getBalance();
			double toBal = toAcc.getBalance();
			double amount = pendingTransfer.getAmount();
			
			if(fromBal < amount) {
				throw new InvalidBalanceException(fromAccId + "does not have enough to transfer."); 
			} else {
				transactionDAO.transferBalance(fromAccId, toAccId, fromBal, toBal, amount, con);
				ret = "Balance has been successfully transferred.";
			}
			logDAO.logUserAction(userId, "approved a transfer", con);
			con.commit();
			return ret;
		}
	}
	
	public String declineTransfer(int transferId, int userId) throws SQLException {
		try(Connection con = ConnectionUtil.getConnection()) {
			String ret = "";
			if (transactionDAO.declineTransfer(transferId, con)) {
				ret = "You have declined this transfer";
			}
			logDAO.logUserAction(userId, "declined a transfer", con);
			return ret;
		}
	}

}
