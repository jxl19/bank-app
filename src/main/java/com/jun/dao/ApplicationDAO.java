package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;

public interface ApplicationDAO {
	public void applyForNewBankAccount(int loginId, double initialBalance, boolean isCheckingAccount, Connection con) throws SQLException, InvalidBalanceException;
}
