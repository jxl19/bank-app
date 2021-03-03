package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.jun.dao.ApplicationDAO;
import com.jun.dao.ApplicationDAOImpl;
import com.jun.dao.CustomerDAO;
import com.jun.dao.CustomerDAOImpl;
import com.jun.dao.LogDAO;
import com.jun.dao.LogDAOImpl;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.Customer;
import com.jun.util.ConnectionUtil;

public class ApplicationService {
	
	public ApplicationDAO applicationDAO;
	public CustomerDAO customerDAO;
	public LogDAO logDAO;

	public ApplicationService() {
		this.applicationDAO = new ApplicationDAOImpl();
		this.customerDAO = new CustomerDAOImpl();
		this.logDAO = new LogDAOImpl();
	}
	
	private static Logger log = Logger.getLogger(ApplicationService.class);
	
	public String applyForNewAccount(int loginId, double initialBalance, boolean isCheckingAccount) throws SQLException, InvalidBalanceException {
		try (Connection con = ConnectionUtil.getConnection()) {
			String action = "";
			if (initialBalance < 0) {
				action = "failed account application - negative balance";
				logDAO.logUserAction(loginId, action, con);
				log.error(loginId + " attempted to apply for a negative balance account");
				throw new InvalidBalanceException("You cannot start a bank account with a negative balance!");
			}
			Customer cust = customerDAO.getCustomerBalance(loginId, con);
			double custBal = cust.getBalance();
			if (custBal < initialBalance) {
				action = "failed account application - starting balance too high";
				logDAO.logUserAction(loginId, action, con);
				log.error(loginId + " attempted to apply for an account with too high a balance");
				throw new InvalidBalanceException("You only have " + custBal + " on hand. You cannot start an account with " + initialBalance + ".");
			}
			applicationDAO.applyForNewBankAccount(loginId, initialBalance, isCheckingAccount, con);
			String accountType = isCheckingAccount ? "checkings" : "savings";
			action = "applied for new " + accountType+ " account";
			logDAO.logUserAction(loginId, action, con);
			log.info(loginId + " successfully applied for an account");
			return "Thank you! Your application for a new account with a starting balance of " + initialBalance + " will be reviewed.";
		}
	}

}
