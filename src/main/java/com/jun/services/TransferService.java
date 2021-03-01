//package com.jun.services;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//
//import com.jun.dao.CardDAO;
//import com.jun.dao.CardDAOImpl;
//import com.jun.dao.TransferDAO;
//import com.jun.dao.TransferDAOImpl;
//import com.jun.exceptions.InvalidBalanceException;
//import com.jun.model.Card;
//import com.jun.util.ConnectionUtil;
//
//public class TransferService {
//	public TransferDAO transferDAO;
//	public CardDAO cardDAO;
//
//	public TransferService() {
//		this.transferDAO = new TransferDAOImpl();
//		this.cardDAO = new CardDAOImpl();
//	}
//	//maybe not since we have to always check to make sure account is owned by person anyway..
//	//we can just return something that tells us if we need approval if we are transfering to another user
//	public void transferWithinAccount(int id, String toAccount, String fromAccount, double amount) throws SQLException, InvalidBalanceException {
//		//we can check the balances here
//		//we can check if login id doesnt match accountno in account
//		  // if that is the case we will need approval
//		try (Connection con = ConnectionUtil.getConnection()) {
//			Card fromAcc = cardDAO.getCardInfo(fromAccount, con);
//			Card toAcc = cardDAO.getCardInfo(toAccount, con);
//			double fromBal = fromAcc.getBalance();
//			double toBal = toAcc.getBalance();
//			if (fromBal< amount) {
//				throw new InvalidBalanceException("You do not have enough in your account to make this transfer");
//			} else {
//				//only if loginid matches accountno
//				transferDAO.transferWithinAccount(fromAccount, toAccount, fromBal, toBal, amount, con);
//			}
//		}
//	}
//
//}
