package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.dao.AccountDAO;
import com.jun.dao.AccountDAOImpl;
import com.jun.dao.LogDAO;
import com.jun.dao.LogDAOImpl;
import com.jun.exceptions.CardNotFoundException;
import com.jun.model.Account;
import com.jun.util.ConnectionUtil;

public class AccountService {

	public AccountDAO accountDAO;
	public LogDAO logDAO;
	public AccountService() {
		this.accountDAO = new AccountDAOImpl();
		this.logDAO = new LogDAOImpl();
	}

	public Account getAccountInfo(String accountNum, int userId) throws SQLException, CardNotFoundException{
		try (Connection con = ConnectionUtil.getConnection()) {
			Account card = accountDAO.getCardInfo(accountNum, con);
			
			if (card == null) {
				throw new CardNotFoundException("The card id: " + accountNum +" does not exist.");
			}
			return card;
		}
	}
	
}
