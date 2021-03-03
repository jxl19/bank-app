package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jun.dao.LogDAO;
import com.jun.dao.LogDAOImpl;
import com.jun.model.TransactionLogs;
import com.jun.util.ConnectionUtil;

public class EmployeeService {

	public LogDAO logDAO;
	public EmployeeService() {
		this.logDAO = new LogDAOImpl();
	}
	
	public ArrayList<TransactionLogs> getTransactions(int userId) throws SQLException {
		try (Connection con = ConnectionUtil.getConnection()) {
			ArrayList<TransactionLogs> logs = logDAO.getUserLogs(userId, con);
			
			return logs;
		}
	}
}
