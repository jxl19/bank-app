package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jun.model.Account;

public interface AccountDAO {
	public Account getAccountInfo(String accountNum, Connection con) throws SQLException; 
	public ArrayList<Account> getAllUserAccounts(int userId, Connection con) throws SQLException; 
}
