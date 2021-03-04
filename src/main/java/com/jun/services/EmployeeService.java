package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;

import com.jun.dao.CustomerDAO;
import com.jun.dao.CustomerDAOImpl;
import com.jun.dao.EmployeeDAO;
import com.jun.dao.EmployeeDAOImpl;
import com.jun.dao.LogDAO;
import com.jun.dao.LogDAOImpl;
import com.jun.exceptions.ApplicationNotFoundException;
import com.jun.exceptions.InvalidApplicationException;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.exceptions.UserNotFoundException;
import com.jun.model.ApplicationReview;
import com.jun.model.TransactionLogs;
import com.jun.util.ConnectionUtil;

public class EmployeeService {

	public LogDAO logDAO;
	public EmployeeDAO EmployeeDAO;
	public CustomerDAO customerDAO;
	
	public EmployeeService() {
		this.EmployeeDAO = new EmployeeDAOImpl();
		this.customerDAO = new CustomerDAOImpl();
		this.logDAO = new LogDAOImpl();
	}
	
	private static Logger log = Logger.getLogger(EmployeeService.class);
	
	public ArrayList<TransactionLogs> getTransactions(int userId) throws SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {
			ArrayList<TransactionLogs> logs = logDAO.getUserLogs(userId, con);
			log.info(userId + " successfully got a log of transactions");
			return logs;
		}
	}
	
	public ApplicationReview reviewApplications() throws ApplicationNotFoundException, SQLException{
		try (Connection con = ConnectionUtil.getConnection()) {
			ApplicationReview applicationReview;
			applicationReview = EmployeeDAO.reviewCustomerApplication(con);
			
			if (applicationReview == null) {
				log.info("Application was not found for review");
				throw new ApplicationNotFoundException("No applications were found");
			}
			
			return applicationReview;
		}
	}
	
	public boolean approveAccount(int userId, int appId, double initialBalance, boolean isCheckingAccount) throws SQLException, InvalidBalanceException, InvalidApplicationException, UserNotFoundException {
		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false);
			boolean ret = true;
			boolean validApplication = false;
			double custBal = customerDAO.getCustomer(userId, con).getBalance();
			validApplication = EmployeeDAO.checkIfValidApplication(appId, con);
			if (!validApplication) {
				log.info("Application " + appId + " no longer pending");
				throw new InvalidApplicationException("This application is no longer pending.");
			}
			if (custBal < initialBalance) {
				ret = false;
				log.info(userId + " does not have enough to create this account");
				throw new InvalidBalanceException("Customer does not have enough cash to have this starting balance");
			}
			
			int firstDigit = ThreadLocalRandom.current().nextInt(3, 6 + 1);
			StringBuilder accountNumber = new StringBuilder();
			accountNumber.append(firstDigit);
			int i = 1;
			while (i <= 15) {
				accountNumber.append(ThreadLocalRandom.current().nextInt(0, 9 + 1));
				i++;
			}
			log.info("Application " + appId + " had been approved");
			EmployeeDAO.approveApplication(userId, appId, initialBalance, isCheckingAccount, accountNumber.toString(), con);
			customerDAO.updateCustomerBalance(userId, initialBalance, custBal, con);
			con.commit();
			return ret;
		}
	}
	
	public boolean rejectAccount(int appId) throws SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {
			EmployeeDAO.rejectAccount(appId, con);
			log.info("Application " + appId + " has been rejected");
			return false;
		}
	}
}
