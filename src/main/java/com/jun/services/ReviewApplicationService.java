package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.CustomerDAO;
import com.jun.dao.CustomerDAOImpl;
import com.jun.dao.ReviewApplicationDAO;
import com.jun.dao.ReviewApplicationDAOImpl;
import com.jun.exceptions.ApplicationNotFoundException;
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

	public ApplicationReview reviewApplications() throws ApplicationNotFoundException, SQLException{
		try (Connection con = ConnectionUtil.getConnection()) {
			ApplicationReview applicationReview;
			applicationReview = reviewApplicationDAO.reviewCustomerApplication(con);
			
			if (applicationReview == null) {
				throw new ApplicationNotFoundException("There are no new applications to review");
			}
			System.out.println("inside service" + applicationReview);
			return applicationReview;
		}
	}
	
	public boolean approveAccount(int id, int appId, double initialBalance) throws SQLException, InvalidBalanceException {
		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false);
			boolean ret = true;
			Customer cust = null;
			cust = customerDAO.getCustomerBalance(id, con);
			if (cust.getBalance() < initialBalance) {
				ret = false;
				throw new InvalidBalanceException("Customer does not have enough cash to have this starting balance");
			}
			reviewApplicationDAO.approveApplication(id, appId, initialBalance, con);
			con.commit();
			return ret;
		}
		
	}
}
