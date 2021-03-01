package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.CustomerDAO;
import com.jun.dao.CustomerDAOImpl;
import com.jun.dao.ReviewApplicationDAO;
import com.jun.dao.ReviewApplicationDAOImpl;
import com.jun.exceptions.ApplicationNotFoundException;
import com.jun.exceptions.InvalidApplicationException;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.ApplicationReview;
import com.jun.model.Customer;
import com.jun.util.ConnectionUtil;

public class ReviewApplicationService {

	public ReviewApplicationDAO reviewApplicationDAO;
	public CustomerDAO customerDAO;
	
	public ReviewApplicationService() {
		this.reviewApplicationDAO = new ReviewApplicationDAOImpl();
		this.customerDAO = new CustomerDAOImpl();
	}

	public ApplicationReview reviewApplications() throws ApplicationNotFoundException, SQLException, NullPointerException{
		try (Connection con = ConnectionUtil.getConnection()) {
			ApplicationReview applicationReview;
			applicationReview = reviewApplicationDAO.reviewCustomerApplication(con);
			
//			if (applicationReview == null) {
//				throw new NullPointerException("There are no new applications to review");
//			}
			System.out.println("inside service" + applicationReview);
			return applicationReview;
		}
	}
	
	public boolean approveAccount(int id, int appId, double initialBalance, boolean isCheckingAccount) throws SQLException, InvalidBalanceException, InvalidApplicationException {
		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false);
			boolean ret = true;
			boolean validApplication = false;
			Customer cust = null;
			cust = customerDAO.getCustomerBalance(id, con);
			validApplication = reviewApplicationDAO.checkIfValidApplication(appId, con);
			if (!validApplication) throw new InvalidApplicationException("This application is no longer pending.");
			if (cust.getBalance() < initialBalance) {
				ret = false;
				throw new InvalidBalanceException("Customer does not have enough cash to have this starting balance");
			}
			reviewApplicationDAO.approveApplication(id, appId, initialBalance, isCheckingAccount, con);
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
