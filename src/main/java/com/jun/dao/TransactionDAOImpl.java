package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.PendingTransfer;
import com.jun.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO {
	
	@Override
	public Transaction updateBalance(String accountNum, String transactionType, double amount, Connection con) throws SQLException, NumberFormatException, InvalidBalanceException{
		Transaction transaction = null;
		String getBalSql = "SELECT balance FROM bank.account WHERE account_no = ?";
		String updateBalSql = "UPDATE bank.account SET balance = ? WHERE account_no = ?";
		
		PreparedStatement balPS = con.prepareStatement(getBalSql);
		PreparedStatement updatePS = con.prepareStatement(updateBalSql);
		
		balPS.setString(1, accountNum);
		updatePS.setString(2, accountNum);
		ResultSet balRS = balPS.executeQuery();
		double accountBalance = 0; 
		
		if (balRS.next()) {
			accountBalance = balRS.getDouble("balance");
		}
		
		if (transactionType == "Deposit") {
			accountBalance += amount;
			updatePS.setDouble(1, accountBalance);
			transaction = new Transaction(String.valueOf(accountBalance));
		} else if (transactionType == "Withdraw") {
			if (accountBalance - amount >= 0) {
				accountBalance -= amount;
				updatePS.setDouble(1, accountBalance);
				transaction = new Transaction(String.valueOf(accountBalance));
			} else {
				throw new InvalidBalanceException("There is not enough balance to complete this transaction");
			}
		}
		updatePS.executeUpdate();
		
		return transaction;
	}
	
	@Override
	public boolean transferBalance(String fromAccount, String toAccount, double fromBalance, double toBalance, double amount, Connection con) throws SQLException {
		String fromSql = "UPDATE bank.account SET balance = ? WHERE account_no = ?";
		String toSql = "UPDATE bank.account SET balance = ? WHERE account_no =?";	
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
	public boolean requestTransfer(String fromAccount, String toAccount, double amount, int toAccountId, Connection con)
			throws SQLException {
		String sql = "INSERT INTO bank.pending_transfers(from_account, to_account, amount, account_id) VALUES (?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, fromAccount);
		ps.setString(2, toAccount);
		ps.setDouble(3, amount);
		ps.setInt(4, toAccountId);
		ps.executeUpdate();
		
		return true;
	}

	@Override
	public ArrayList<PendingTransfer> checkPendingTransfers(int accountId, Connection con) throws SQLException {
		ArrayList<PendingTransfer> pendingTransfer = new ArrayList<>();
		String sql = "SELECT * FROM bank.pending_transfers WHERE account_id = ? AND pending = TRUE";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, accountId);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			String fromAccountId = rs.getString("from_account");
			String toAccountId = rs.getString("to_account");
			double amount = rs.getDouble("amount");
			boolean pending = rs.getBoolean("pending");
			int transferId = rs.getInt("transfer_id");
			pendingTransfer.add(new PendingTransfer(fromAccountId, toAccountId, amount, pending, transferId));
			
		}
		return pendingTransfer;
	}

	@Override
	public PendingTransfer approveTransfer(int transferId, Connection con) throws SQLException {
		PendingTransfer pendingTransfer = null;
		String updateSql = "UPDATE bank.pending_transfers SET pending = FALSE WHERE transfer_id = ?";
		PreparedStatement ps = con.prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, transferId);
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()) {
			String fromAccountId = rs.getString("from_account");
			String toAccountId = rs.getString("to_account");
			double amount = rs.getDouble("amount");
			boolean pending = rs.getBoolean("pending");
			int tId = rs.getInt("transfer_id");
			pendingTransfer = new PendingTransfer(fromAccountId, toAccountId, amount, pending, tId);
		}
		return pendingTransfer;
	}

	@Override
	public boolean declineTransfer(int transferId, Connection con) throws SQLException {
		String sql = "UPDATE bank.pending_transfers SET pending = FALSE WHERE transfer_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, transferId);
		ps.executeUpdate();
		
		return true;
	}


}
