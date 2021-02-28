package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.exceptions.InvalidBalanceException;

public interface ApplicationDAO {
	public double applyForNewBankAccount(int loginId, double initialBalance, Connection con) throws SQLException, InvalidBalanceException;
}
