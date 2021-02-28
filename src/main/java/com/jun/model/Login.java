package com.jun.model;

import java.util.Objects;

public class Login {

	private boolean isEmployee;
	private int loginId;
	
	public Login() {
		super();
	}

	public Login(boolean isEmployee, int loginId) {
		super();
		this.isEmployee = isEmployee;
		this.loginId = loginId;
	}

	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isEmployee, loginId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Login other = (Login) obj;
		return isEmployee == other.isEmployee && loginId == other.loginId;
	}

	@Override
	public String toString() {
		return "Login [isEmployee=" + isEmployee + ", loginId=" + loginId + "]";
	}

}
