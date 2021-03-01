package com.jun.model;

import java.util.Objects;

public class Account {
	private String accountNum;
	private double balance;
	private int accountId;
	private boolean isCheckingAccount;
	public Account(String accountNum, double balance, int accountId, boolean isCheckingAccount) {
		super();
		this.accountNum = accountNum;
		this.balance = balance;
		this.accountId = accountId;
		this.isCheckingAccount = isCheckingAccount;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public boolean isCheckingAccount() {
		return isCheckingAccount;
	}
	public void setCheckingAccount(boolean isCheckingAccount) {
		this.isCheckingAccount = isCheckingAccount;
	}
	@Override
	public int hashCode() {
		return Objects.hash(accountId, accountNum, balance, isCheckingAccount);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return accountId == other.accountId && Objects.equals(accountNum, other.accountNum)
				&& Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& isCheckingAccount == other.isCheckingAccount;
	}
	@Override
	public String toString() {
		return "Account [accountNum=" + accountNum + ", balance=" + balance + ", accountId=" + accountId
				+ ", isCheckingAccount=" + isCheckingAccount + "]";
	}

}
