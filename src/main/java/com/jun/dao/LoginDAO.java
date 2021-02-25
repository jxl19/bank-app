package com.jun.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.jun.model.User;

public interface LoginDAO {
	public User authenticateUser(String username, String password, Connection con) throws SQLException;
}
