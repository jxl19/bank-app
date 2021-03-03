package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jun.dao.CustomerDAO;
import com.jun.dao.CustomerDAOImpl;
import com.jun.dao.EmployeeDAO;
import com.jun.dao.EmployeeDAOImpl;
import com.jun.dao.LogDAO;
import com.jun.dao.LogDAOImpl;
import com.jun.exceptions.ApplicationNotFoundException;
import com.jun.exceptions.InvalidApplicationException;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.ApplicationReview;
import com.jun.model.Customer;
import com.jun.model.TransactionLogs;
import com.jun.util.ConnectionUtil;

public class EmployeeService {

	public LogDAO logDAO;
	public EmployeeDAO reviewApplicationDAO;
	public CustomerDAO customerDAO;
	
	public EmployeeService() {
		this.reviewApplicationDAO = new EmployeeDAOImpl();
		this.customerDAO = new CustomerDAOImpl();
		this.logDAO = new LogDAOImpl();
	}
	
	public ArrayList<TransactionLogs> getTransactions(int userId) throws SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {
			ArrayList<TransactionLogs> logs = logDAO.getUserLogs(userId, con);
			
			return logs;
		}
	}
	
	public ApplicationReview reviewApplications() throws ApplicationNotFoundException, SQLException, NullPointerException{
		try (Connection con = ConnectionUtil.getConnection()) {
			ApplicationReview applicationReview;
			applicationReview = reviewApplicationDAO.reviewCustomerApplication(con);
			return applicationReview;
		}
	}
	
	public boolean approveAccount(int userId, int appId, double initialBalance, boolean isCheckingAccount) throws SQLException, InvalidBalanceException, InvalidApplicationException {
		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false);
			boolean ret = true;
			boolean validApplication = false;
			Customer cust = null;
			cust = customerDAO.getCustomerBalance(userId, con);
			validApplication = reviewApplicationDAO.checkIfValidApplication(appId, con);
			if (!validApplication) throw new InvalidApplicationException("This application is no longer pending.");
			if (cust.getBalance() < initialBalance) {
				ret = false;
//				logDAO.logUserAction(userId, "tried to approve account - not enough balance", con);
				throw new InvalidBalanceException("Customer does not have enough cash to have this starting balance");
			}
			reviewApplicationDAO.approveApplication(userId, appId, initialBalance, isCheckingAccount, con);
			con.commit();
			return ret;
		}
	}
	
	public boolean rejectAccount(int appId) throws SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {
			reviewApplicationDAO.rejectAccount(appId, con);
			return false;
		}
	}
}
