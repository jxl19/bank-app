package com.jun.model;

import java.util.Objects;

public class Transaction {
	
	private String transactionType;
	private String amount;
	
	public Transaction(String transactionType, String amount) {
		super();
		this.transactionType = transactionType;
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, transactionType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(transactionType, other.transactionType);
	}

	@Override
	public String toString() {
		return "Transaction [transactionType=" + transactionType + ", amount=" + amount + "]";
	}



}
