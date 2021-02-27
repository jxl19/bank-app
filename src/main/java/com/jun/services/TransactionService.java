package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.CardDAO;
import com.jun.dao.CardDAOImpl;
import com.jun.dao.TransactionDAO;
import com.jun.dao.TransactionDAOImpl;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.Card;
import com.jun.model.Transaction;
import com.jun.util.ConnectionUtil;

public class TransactionService {

	public TransactionDAO transactionDAO;
	public CardDAO cardDAO;
	public TransactionService() {
		this.transactionDAO = new TransactionDAOImpl();
		this.cardDAO = new CardDAOImpl();
	}
	
	public Transaction updateBalance(String cardNum, String transactionType, String amount) throws SQLException, NumberFormatException, InvalidBalanceException {
		try (Connection con = ConnectionUtil.getConnection()) {
			Transaction transaction =transactionDAO.updateBalance(cardNum, transactionType, amount, con);
//			Card card = cardDAO.getCardInfo(cardNum, con);
			
			return transaction;
		}
	}

}
