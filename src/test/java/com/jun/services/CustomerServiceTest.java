package com.jun.services;

import static org.junit.Assert.assertEquals;
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
import com.jun.exceptions.UserNotFoundException;
import com.jun.model.Customer;
import com.jun.util.ConnectionUtil;

public class CustomerServiceTest {

	public CustomerService customerService;

	public static CustomerDAO customerDAO;
	public static Connection mockConnection;
	public static MockedStatic<ConnectionUtil> mockedStatic;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		customerDAO = mock(CustomerDAO.class);
		mockConnection = mock(Connection.class);

		when(customerDAO.getCustomerAccountsById(1, mockConnection)).thenReturn(new Customer(1, 1000.00));
	}

	@Before
	public void setUp() throws Exception {
		customerService = new CustomerService(customerDAO);
	}

	@Test
	public void testValidCustomerId() throws UserNotFoundException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			boolean cust = customerService.checkValidCustomer(1);
			boolean expected = true;
			assertEquals(expected, cust);
		}
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testUserNotFound() throws UserNotFoundException, SQLException {
		try (MockedStatic<ConnectionUtil> mockedStatic = Mockito.mockStatic(ConnectionUtil.class)) {
			mockedStatic.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			customerService.checkValidCustomer(2);
		}
	}
}
