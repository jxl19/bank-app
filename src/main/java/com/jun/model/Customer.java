package com.jun.model;

import java.util.List;
import java.util.Objects;

public class Customer {
	private List<String> cardNo;
	private int loginId;
	private double balance;
	
	public Customer(int loginId, double balance) {
		super();
		this.loginId = loginId;
		this.balance = balance;
	}
	
	public Customer(List<String> cardNo, int loginId, double balance) {
		super();
		this.cardNo = cardNo;
		this.loginId = loginId;
		this.balance = balance;
	}
	public List<String> getCardNo() {
		return cardNo;
	}
	public void setCardNo(List<String> cardNo) {
		this.cardNo = cardNo;
	}
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public int hashCode() {
		return Objects.hash(balance, cardNo, loginId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& Objects.equals(cardNo, other.cardNo) && loginId == other.loginId;
	}
	@Override
	public String toString() {
		return "Customer [cardNo=" + cardNo + ", loginId=" + loginId + ", balance=" + balance + "]";
	}
	
	
//	public Customer(List<String> cardNo, int loginId) {
//		super();
//		this.cardNo = cardNo;
//		this.loginId = loginId;
//	}
//	public List<String> getCardNo() {
//		return cardNo;
//	}
//	public void setCardNo(List<String> cardNo) {
//		this.cardNo = cardNo;
//	}
//	public int getLoginId() {
//		return loginId;
//	}
//	public void setLoginId(int loginId) {
//		this.loginId = loginId;
//	}
//	@Override
//	public int hashCode() {
//		return Objects.hash(cardNo, loginId);
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Customer other = (Customer) obj;
//		return Objects.equals(cardNo, other.cardNo) && loginId == other.loginId;
//	}
//	@Override
//	public String toString() {
//		return "Customer [cardNo=" + cardNo + ", loginId=" + loginId + "]";
//	} 
	
	
}
