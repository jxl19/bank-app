package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.ApplicationDAO;
import com.jun.dao.ApplicationDAOImpl;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.util.ConnectionUtil;

public class ApplicationService {
	
	public ApplicationDAO applicationDAO;

	public ApplicationService() {
		this.applicationDAO = new ApplicationDAOImpl();
	}
	
	public String applyForNewAccount(int loginId, double initialBalance) throws SQLException, InvalidBalanceException {
		if (initialBalance < 0) {
			throw new InvalidBalanceException("You cannot start a bank account with a negative balance!");
		}
		try (Connection con = ConnectionUtil.getConnection()) {
			
			double cash = applicationDAO.applyForNewBankAccount(loginId, initialBalance, con);
			
			if (cash < initialBalance) {
				throw new InvalidBalanceException("You only have " + cash + " on hand. You cannot start an account with " + initialBalance + ".");
			}
			
			return "Thank you! Your application for a new account with a starting balance of " + initialBalance + " will be reviewed.";
		}
	}

}
