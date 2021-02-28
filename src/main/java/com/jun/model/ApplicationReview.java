package com.jun.model;

import java.util.Objects;

public class ApplicationReview {
	public int appId;
	public int loginId;
	public String firstName;
	public String lastName;
	public int credit;
	public double initialBalance;
	public ApplicationReview(int appId, int loginId, String firstName, String lastName, int credit,
			double initialBalance) {
		super();
		this.appId = appId;
		this.loginId = loginId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.credit = credit;
		this.initialBalance = initialBalance;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public double getInitialBalance() {
		return initialBalance;
	}
	public void setInitialBalance(double initialBalance) {
		this.initialBalance = initialBalance;
	}
	@Override
	public int hashCode() {
		return Objects.hash(appId, credit, firstName, initialBalance, lastName, loginId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationReview other = (ApplicationReview) obj;
		return appId == other.appId && credit == other.credit && Objects.equals(firstName, other.firstName)
				&& Double.doubleToLongBits(initialBalance) == Double.doubleToLongBits(other.initialBalance)
				&& Objects.equals(lastName, other.lastName) && loginId == other.loginId;
	}
	@Override
	public String toString() {
		return "ApplicationReview [appId=" + appId + ", loginId=" + loginId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", credit=" + credit + ", initialBalance=" + initialBalance + "]";
	}
}
