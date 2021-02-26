package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.model.Card;

public interface CardDAO {
	public Card getCardInfo(String cardNum, Connection con) throws SQLException; 
}
