package com.jun.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Customer {
	private List<String> cardNo;
	private int personId;
	public Customer(List<String> cardNo, int personId) {
		super();
		this.cardNo = cardNo;
		this.personId = personId;
	}
	public List<String> getCardNo() {
		return cardNo;
	}
	public void setCardNo(List<String> cardNo) {
		this.cardNo = cardNo;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cardNo, personId);
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
		return Objects.equals(cardNo, other.cardNo) && personId == other.personId;
	}
	@Override
	public String toString() {
		return "Customer [cardNo=" + cardNo + ", personId=" + personId + "]";
	} 
	
	
}
