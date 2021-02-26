package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jun.model.Card;

public class CardDAOImpl implements CardDAO {

	@Override
	public Card getCardInfo(String cardNum, Connection con) throws SQLException {
		Card card = null;
		String sql = "SELECT * FROM bank.card WHERE card_no = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, cardNum);
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			double balance = rs.getDouble("balance");
			
			card = new Card(cardNum, balance);
		}
	
		return card;
	}

}
