package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.model.Account;

public interface AccountDAO {
	public Account getCardInfo(String accountNum, Connection con) throws SQLException; 
}
