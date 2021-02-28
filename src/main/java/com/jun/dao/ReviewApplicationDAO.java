package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.model.ApplicationReview;

public interface ReviewApplicationDAO {
	public ApplicationReview reviewCustomerApplication(Connection con) throws SQLException;
}
