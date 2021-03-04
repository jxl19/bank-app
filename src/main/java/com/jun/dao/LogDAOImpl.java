package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.jun.model.TransactionLogs;

public class LogDAOImpl implements LogDAO {

	@Override
	public void logUserAction(int userId, String action, Connection con) throws SQLException {
		String sql = "INSERT INTO bank.transaction_logs (transaction_time, user_id, \"action\") VALUES (?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setObject(1, LocalDateTime.now());
		ps.setInt(2, userId);
		ps.setString(3, action);
		int logged = ps.executeUpdate();
		if (logged != 1) {
			throw new SQLException("Logging was not successful");
		}
	}

	@Override
	public ArrayList<TransactionLogs> getUserLogs(int userId, Connection con) throws SQLException {
		ArrayList<TransactionLogs> userLogs = new ArrayList<>();
		String sql = "SELECT * FROM bank.transaction_logs WHERE user_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int tableId = rs.getInt("table_id");
			String transactionTime = rs.getString("transaction_time").split("\\.")[0];
			String userAction = rs.getString("action");
			userLogs.add(new TransactionLogs(tableId, transactionTime, userId, userAction));
		}
		return userLogs;
	}

}
