package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.AccountDAO;
import com.jun.dao.AccountDAOImpl;
import com.jun.exceptions.CardNotFoundException;
import com.jun.model.Account;
import com.jun.util.ConnectionUtil;

public class AccountService {

	public AccountDAO cardDAO;
	public AccountService() {
		this.cardDAO = new AccountDAOImpl();
	}

	public double getBalanceByCardNum(String accountNum) throws SQLException, CardNotFoundException{
		try (Connection con = ConnectionUtil.getConnection()) {
			Account card = cardDAO.getCardInfo(accountNum, con);
			
			if (card == null) {
				throw new CardNotFoundException("The card id: " + accountNum +" does not exist.");
			}
			
			return card.getBalance();
		}
	}
	
}
