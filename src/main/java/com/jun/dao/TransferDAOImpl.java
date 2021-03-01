//package com.jun.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//
//
//public class TransferDAOImpl implements TransferDAO {
//	@Override
//	public boolean transferWithinAccount(String fromAccount, String toAccount, double fromBalance, double toBalance, double amount, Connection con) throws SQLException {
//		String fromSql = "UPDATE bank.account SET balance = ? WHERE account_no = ?";
//		String toSql = "UPDATE bank.account SET balance = ? WHERE account_no =?";	
//		
//		PreparedStatement fromPS = con.prepareStatement(fromSql);
//		fromPS.setDouble(1, fromBalance - amount);
//		fromPS.setString(2, fromAccount);
//		fromPS.executeUpdate();
//		
//		PreparedStatement toPS = con.prepareStatement(toSql);
//		toPS.setDouble(1, toBalance + amount);
//		toPS.setString(2, fromAccount);
//		toPS.executeUpdate();
//		
//
//     	//invalid balance exception if the account its coming from does not have enough
//		//CardDAOImpl can check balance. we can query this in our service layer to throw invalid bal if it happen
//		return true;
//	}
//
//}
