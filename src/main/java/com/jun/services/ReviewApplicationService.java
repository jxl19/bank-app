package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.ReviewApplicationDAO;
import com.jun.dao.ReviewApplicationDAOImpl;
import com.jun.exceptions.ApplicationNotFoundException;
import com.jun.model.ApplicationReview;
import com.jun.util.ConnectionUtil;

public class ReviewApplicationService {

	public ReviewApplicationDAO reviewApplicationDAO;
	
	public ReviewApplicationService() {
		this.reviewApplicationDAO = new ReviewApplicationDAOImpl();
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
}
