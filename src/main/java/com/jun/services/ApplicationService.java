package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.ApplicationDAO;
import com.jun.dao.ApplicationDAOImpl;
import com.jun.dao.CustomerDAO;
import com.jun.dao.CustomerDAOImpl;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.Customer;
import com.jun.util.ConnectionUtil;

public class ApplicationService {
	
	public ApplicationDAO applicationDAO;
	public CustomerDAO customerDAO;

	public ApplicationService() {
		this.applicationDAO = new ApplicationDAOImpl();
		this.customerDAO = new CustomerDAOImpl();
	}
	
	public String applyForNewAccount(int loginId, double initialBalance, boolean isCheckingAccount) throws SQLException, InvalidBalanceException {
		if (initialBalance < 0) {
			throw new InvalidBalanceException("You cannot start a bank account with a negative balance!");
		}
		try (Connection con = ConnectionUtil.getConnection()) {
			
			Customer cust = customerDAO.getCustomerBalance(loginId, con);
			double custBal = cust.getBalance();
			if (custBal < initialBalance) {
				throw new InvalidBalanceException("You only have " + custBal + " on hand. You cannot start an account with " + initialBalance + ".");
			}
			applicationDAO.applyForNewBankAccount(loginId, initialBalance, isCheckingAccount, con);
			
			
			return "Thank you! Your application for a new account with a starting balance of " + initialBalance + " will be reviewed.";
		}
	}

}
