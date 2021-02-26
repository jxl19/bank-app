package com.jun.model;

import java.util.Objects;

public class Login {

	private boolean isAdmin;
	private int loginId;
	
	public Login() {
		super();
	}

	public Login(boolean isAdmin, int loginId) {
		super();
		this.isAdmin = isAdmin;
		this.loginId = loginId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	@Override
	public String toString() {
		return "Login [isAdmin=" + isAdmin + ", loginId=" + loginId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(isAdmin, loginId);
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
		return isAdmin == other.isAdmin && loginId == other.loginId;
	}

}
