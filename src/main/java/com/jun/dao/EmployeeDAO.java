package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.exceptions.InvalidApplicationException;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.ApplicationReview;

public interface EmployeeDAO {
	public ApplicationReview reviewCustomerApplication(Connection con) throws SQLException;
	public boolean approveApplication(int loginId, int appId, double intialBalance, boolean isCheckingAccount, String accountNum, Connection con) throws InvalidBalanceException, SQLException;
	public boolean rejectAccount(int appId, Connection con) throws SQLException;
	public boolean checkIfValidApplication(int appId, Connection con) throws InvalidApplicationException, SQLException;
}
