package com.jun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jun.model.Customer;

public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public Customer getCustomerById(int id, Connection con) throws SQLException {
		Customer cust = null;
		String sql = "SELECT * FROM bank.card WHERE login_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setLong(1, id);
		
		ResultSet rs = pstmt.executeQuery();
		List<String> cards = new ArrayList<>();
		
		while (rs.next()) {
			cards.add(rs.getString("card_no"));
		}
		if (cards.size() < 0) {			
			cust = new Customer(cards, id);
		}

		return cust;
	}
	
//	@Override
//	public List<Customer> getCardNumbersById(int id, Connection con) throws SQLException {
//			String sql = "SELECT card_no FROM bank.card_account WHERE person_id = (SELECT person_id FROM person WHERE login_id = ?)";
//			PreparedStatement pstmt = con.prepareStatement(sql);
//			pstmt.setLong(1, id);
//			
//			ResultSet rs = pstmt.executeQuery();
//			List<Customer> cards = new ArrayList<>();
//			
//			while (rs.next()) {
//				System.out.println(rs.getString("card_no"));
//			}
//			
//			return cards;
//	}

}
