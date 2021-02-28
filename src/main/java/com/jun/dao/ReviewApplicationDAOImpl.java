package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jun.model.ApplicationReview;

public class ReviewApplicationDAOImpl implements ReviewApplicationDAO {

	@Override
	public ApplicationReview reviewCustomerApplication(Connection con) throws SQLException{
		ApplicationReview applicationReview = null;
		String sql = "SELECT * FROM bank.pending_applications WHERE pending = true LIMIT 1";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			int appId = rs.getInt("app_id");
			int loginId = rs.getInt("login_id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			int credit = rs.getInt("credit");
			double initBalance = rs.getDouble("initial_balance");
			
			applicationReview = new ApplicationReview(appId, loginId, firstName, lastName, credit, initBalance); 
		}
		return applicationReview;
	}

}
