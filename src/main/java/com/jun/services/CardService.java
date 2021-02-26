package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.CardDAO;
import com.jun.dao.CardDAOImpl;
import com.jun.exceptions.CardNotFoundException;
import com.jun.model.Card;
import com.jun.util.ConnectionUtil;

public class CardService {

	public CardDAO cardDAO;
	public CardService() {
		this.cardDAO = new CardDAOImpl();
	}

	public double getBalanceByCardNum(String cardNum) throws SQLException, CardNotFoundException{
		try (Connection con = ConnectionUtil.getConnection()) {
			Card card = cardDAO.getCardInfo(cardNum, con);
			
			if (card == null) {
				throw new CardNotFoundException("The card id: " + cardNum +" does not exist.");
			}
			
			return card.getBalance();
		}
	}
	
}
