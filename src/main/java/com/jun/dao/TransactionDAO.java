package com.jun.dao;

import java.sql.Connection;

import com.jun.model.Transaction;

public interface TransactionDAO {
	public Transaction updateBalance(String transactionType, String amount, Connection con);
}
