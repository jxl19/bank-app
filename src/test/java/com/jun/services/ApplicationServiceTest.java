package com.jun.services;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;

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
import com.jun.dao.ApplicationDAO;
import com.jun.dao.CustomerDAO;
import com.jun.dao.LogDAO;
import com.jun.exceptions.InvalidBalanceException;
import com.jun.main.Application;
import com.jun.model.Account;
import com.jun.util.ConnectionUtil;

public class ApplicationServiceTest {

	public ApplicationService applicationService;
	
	public static ApplicationDAO applicationDAO;
	public static LogDAO logDAO;
	public static CustomerDAO customerDAO;
	public static Connection mockConnection;
	public static MockedStatic<ConnectionUtil> mockedStatic;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationDAO = mock(ApplicationDAO.class);
		logDAO = mock(LogDAO.class);
		customerDAO = mock(CustomerDAO.class);
		mockConnection = mock(Connection.class);
		doNothing().when(applicationDAO).applyForNewBankAccount(eq(1), eq(1000.00), eq(false), eq(mockConnection));
		doNothing().when(logDAO).logUserAction(eq(1), eq("test"), eq(mockConnection));
//		when(customerDAO).getCustomer(1, mockConnection).thenReturn()
	}
	
	@Before 
	public void setUp() throws Exception {
		applicationService = new ApplicationService(applicationDAO, logDAO);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test 
	public void testValidAccountApplication() throws InvalidBalanceException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			boolean actual = applicationService.applyForNewAccount(1, 10000.00, false);
			
			boolean expected = true;
			
			assertEquals(expected, actual);
			
		}
	}

	@Test(expected = InvalidBalanceException.class)
	public void testInvalidAccountApplication() throws InvalidBalanceException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			boolean actual = applicationService.applyForNewAccount(1, -1000, false);
		}
	}

}
