package com.jun.model;

public class User {
	private Boolean isAdmin;
	private int userId;
	private int loginId;
	private double balance;
	
	public User(Boolean isAdmin, int userId, int loginId) {
		super();
		this.isAdmin = isAdmin;
		this.userId = userId;
		this.loginId = loginId;
	}
	public User(Boolean isAdmin, int userId, int loginId, double balance) {
		this(isAdmin, userId, loginId);
		this.balance = balance;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public String toString() {
		return "User [isAdmin=" + isAdmin + ", userId=" + userId + ", loginId=" + loginId + ", balance=" + balance
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((isAdmin == null) ? 0 : isAdmin.hashCode());
		result = prime * result + loginId;
		result = prime * result + userId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (isAdmin == null) {
			if (other.isAdmin != null)
				return false;
		} else if (!isAdmin.equals(other.isAdmin))
			return false;
		if (loginId != other.loginId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
	
}
