package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jun.model.TransactionLogs;

public interface LogDAO {
	public void logUserAction(int userId, String action, Connection con) throws SQLException; 
	public ArrayList<TransactionLogs> getUserLogs(int userId, Connection con) throws SQLException;
}
