package com.jun.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

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
	
	public AccountService(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	
	private static Logger log = Logger.getLogger(AccountService.class);

	public Account getAccountInfo(String accountNum, int userId) throws CardNotFoundException, SQLException{
		try (Connection con = ConnectionUtil.getConnection()) {
			Account card = accountDAO.getCardInfo(accountNum, con);
			
			if (card == null) {
				log.error("Account " + accountNum + " was not found");
				throw new CardNotFoundException("The account id: " + accountNum +" does not exist.");
			}
			log.info("User " + userId + " successfully got account information");
			return card;
		}
	}
	
}
