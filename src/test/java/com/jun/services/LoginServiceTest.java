package com.jun.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.jun.dao.CustomerDAO;
import com.jun.dao.LogDAO;
import com.jun.dao.LoginDAO;
import com.jun.exceptions.UserAlreadyExistsException;
import com.jun.exceptions.UserNotFoundException;
import com.jun.model.Login;
import com.jun.util.ConnectionUtil;

public class LoginServiceTest {

	public LoginService loginService;
	
	public static LoginDAO loginDAO;
	public static LogDAO logDAO;
	public static CustomerDAO customerDAO;
	public static Connection mockConnection;
	public static MockedStatic<ConnectionUtil> mockedStatic;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		loginDAO = mock(LoginDAO.class);
		logDAO = mock(LogDAO.class);
		customerDAO = mock(CustomerDAO.class);
		mockConnection = mock(Connection.class);
		
		when(loginDAO.authenticateUser(eq("user1"), eq("pw1"), eq(mockConnection))).thenReturn(new Login(false, 1));
		when(loginDAO.createLogin(eq("jlei"), eq("pass"), eq("jlei@test.com"), eq(mockConnection))).thenReturn(3);
		doNothing().when(logDAO).logUserAction(1, "test", mockConnection);
		when(customerDAO.checkValidUnsername("jlei", mockConnection)).thenReturn(true);
	}
	
	@Before 
	public void setUp() throws Exception {
		loginService = new LoginService(loginDAO, logDAO, customerDAO);
	}
	
	@Test
	public void testValidLogin() throws UserNotFoundException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			Login actual = loginService.authenticateUser("user1", "pw1");
			Login expected = new Login(false, 1);
			
			assertEquals(expected, actual);
		}
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testInvalidLogin() throws UserNotFoundException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
		loginService.authenticateUser("user2", "pw2");
		}
	}
	
	@Test
	public void testCreateUser() throws UserAlreadyExistsException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			boolean userCreated = loginService.createUser("jlei", "pass", "Jun", "Lei", "jlei@test.com", false);
			boolean expected = true;
			assertEquals(expected, userCreated);
		}
	}
	
	
}
