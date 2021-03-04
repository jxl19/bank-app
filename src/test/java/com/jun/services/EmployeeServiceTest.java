package com.jun.services;


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
import com.jun.dao.EmployeeDAO;
import com.jun.dao.LogDAO;
import com.jun.exceptions.ApplicationNotFoundException;
import com.jun.util.ConnectionUtil;

public class EmployeeServiceTest {
	
	public EmployeeService employeeService;
	
	public static LogDAO logDAO;
	public static EmployeeDAO employeeDAO;
	public static CustomerDAO customerDAO;
	public static Connection mockConnection;
	public static MockedStatic<ConnectionUtil> mockedStatic;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logDAO = mock(LogDAO.class);
		customerDAO = mock(CustomerDAO.class);
		employeeDAO = mock(EmployeeDAO.class);
		mockConnection = mock(Connection.class);
		
		doNothing().when(logDAO).logUserAction(1, "test", mockConnection);
		when(employeeDAO.rejectAccount(eq(1), eq(mockConnection))).thenReturn(true);

		when(employeeDAO.reviewCustomerApplication(eq(mockConnection))).thenReturn(null);
	}
	
	@Before
	public void setUp() throws Exception {
		employeeService = new EmployeeService(logDAO, employeeDAO, customerDAO);
	}
	
	@Test
	public void testRejectApplication() throws SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			employeeService.rejectAccount(1);
		}
	}
	
	@Test(expected = ApplicationNotFoundException.class)
	public void testApplicationNotFound() throws ApplicationNotFoundException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			employeeService.reviewApplications();
		}
	}
	
}
