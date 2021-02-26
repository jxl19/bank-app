package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.model.Login;

public interface LoginDAO {
	public Login authenticateUser(String username, String password, Connection con) throws SQLException;
}
