package com.jun.services;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.jun.dao.AccountDAO;
import com.jun.exceptions.AccountNotFoundException;
import com.jun.model.Account;
import com.jun.util.ConnectionUtil;

public class AccountServiceTest {

	public AccountService accountService;
	
	public static AccountDAO accountDAO;
	public static Connection mockConnection;
	public static MockedStatic<ConnectionUtil> mockedStatic;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		accountDAO = mock(AccountDAO.class);
		mockConnection = mock(Connection.class);
		
		when(accountDAO.getAccountInfo(eq("5432123456789876"), eq(mockConnection))).thenReturn(new Account("5432123456789876", 1000.00, 1, true));
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		accountService = new AccountService(accountDAO);
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testValidAccount() throws AccountNotFoundException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			Account actual = accountService.getAccountInfo("5432123456789876", 1);
			
			Account expected = new Account("5432123456789876", 1000.00, 1, true);
			
			assertEquals(expected, actual);
		}
	}
	
	@Test(expected = AccountNotFoundException.class)
	public void testInvalidAccount() throws AccountNotFoundException, SQLException {
		try(MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			accountService.getAccountInfo("5432123456789873", 2);
		}
	}
	
}
