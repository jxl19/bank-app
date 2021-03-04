package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jun.dao.AccountDAO;
import com.jun.dao.AccountDAOImpl;
import com.jun.dao.CustomerDAO;
import com.jun.dao.CustomerDAOImpl;
import com.jun.dao.LogDAO;
import com.jun.dao.LogDAOImpl;
import com.jun.dao.TransactionDAO;
import com.jun.dao.TransactionDAOImpl;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.exceptions.InvalidTransferRequestException;
import com.jun.exceptions.UserNotFoundException;
import com.jun.model.Account;
import com.jun.model.PendingTransfer;
import com.jun.model.Transaction;
import com.jun.util.ConnectionUtil;

public class TransactionService {

	public TransactionDAO transactionDAO;
	public AccountDAO accountDAO;
	public LogDAO logDAO;
	public CustomerDAO customerDAO;
	
	public TransactionService() {
		this.transactionDAO = new TransactionDAOImpl();
		this.accountDAO = new AccountDAOImpl();
		this.logDAO = new LogDAOImpl();
		this.customerDAO = new CustomerDAOImpl();
	}
	
	private static Logger log = Logger.getLogger(TransactionService.class);
	
	public Transaction transferBalance(String accountNum, String transactionType, double amount, int userId) throws SQLException, NumberFormatException, InvalidBalanceException, UserNotFoundException {
		if (amount < 0) {
			log.warn("User " + userId + "attempted to transfer a negative balance");
			throw new InvalidBalanceException("You cannot input a negative balance!");
		}
		try (Connection con = ConnectionUtil.getConnection()) {
			double custBal = customerDAO.getCustomer(userId, con).getBalance();
			Transaction transaction = transactionDAO.updateBalance(accountNum, transactionType, amount, custBal, con);
			if(transactionType == "Deposit") {
				customerDAO.updateCustomerBalance(userId, amount, custBal, con);
			} else if(transactionType =="Withdraw") {
				customerDAO.updateCustomerBalance(userId, -amount, custBal, con);
			}
			log.info(userId + " successfully transffered balance");
			logDAO.logUserAction(userId, accountNum +" : "+ transactionType + "ing balance " , con);
			return transaction;
		}
	}
	
	public String transferBalanceToAccount(int userId, String toAccount, String fromAccount, double amount) throws SQLException, InvalidBalanceException, InvalidTransferRequestException, UserNotFoundException {
		if (amount < 0) {
			log.warn("User " + userId + "attempted to transfer a negative balance");
			throw new InvalidBalanceException("You cannot input a negative balance!");
		}
		if (toAccount.equals(fromAccount)) {
			log.warn(userId + "Attempted to transfer to same account");
			throw new InvalidTransferRequestException("You cannot transfer to yourself!");
		}
		try (Connection con = ConnectionUtil.getConnection()) {
			String ret = "";
			String action = "";
			Account toAcc = accountDAO.getAccountInfo(toAccount, con);
			if (toAcc == null) {
				log.info("This account does not exist");
				throw new UserNotFoundException("This account this not exist. Please try again.");
			}
			Account fromAcc = accountDAO.getAccountInfo(fromAccount, con);
			
			double fromBal = fromAcc.getBalance();
			double toBal = toAcc.getBalance();
			int toID = toAcc.getAccountId();
			
			if (fromBal < amount) {
				System.out.println("from: " + fromBal + "amount: " + amount );
				log.warn("Attempted to transfer an amount more than user "+ userId + " currently has");
				throw new InvalidBalanceException("You do not have enough in your account to make this transfer");
			} else {
				if (userId == toID) {
					transactionDAO.transferBalance(fromAccount, toAccount, fromBal, toBal, amount, con);
					ret = "The transfer is completed";
					action = "successfully transferred from " + fromAccount + " to " + toAccount;
					log.info("Successfully transfered from" + fromAccount + " to " + toAccount);
				} else {
					transactionDAO.requestTransfer(fromAccount, toAccount, amount, toID, con);
					ret = "Your transfer is pending";
					action = "requested a transfer";
					log.info("Transfer request pending");
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
			log.info("User " + accountId + " checking pending transfers");
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
			Account fromAcc = accountDAO.getAccountInfo(fromAccId, con);
			Account toAcc = accountDAO.getAccountInfo(toAccId, con);
			double fromBal = fromAcc.getBalance();
			double toBal = toAcc.getBalance();
			double amount = pendingTransfer.getAmount();
			
			if(fromBal < amount) {
				log.warn("Account transfering out does not have enough balance");
				throw new InvalidBalanceException(fromAccId + "does not have enough to transfer."); 
			} else {
				transactionDAO.transferBalance(fromAccId, toAccId, fromBal, toBal, amount, con);
				log.info("Transfer id: " + transferId + " Successfully transferred");
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
			log.info("Transfer id: " + transferId + " declined");
			logDAO.logUserAction(userId, "declined a transfer", con);
			return ret;
		}
	}

}
