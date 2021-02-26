package com.jun.model;

import java.util.Objects;

public class Card {
	public String cardNum;
	public double balance;
	public Card(String cardNum, double balance) {
		super();
		this.cardNum = cardNum;
		this.balance = balance;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public int hashCode() {
		return Objects.hash(balance, cardNum);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& Objects.equals(cardNum, other.cardNum);
	}
	@Override
	public String toString() {
		return "Card [cardNum=" + cardNum + ", balance=" + balance + "]";
	}

}
