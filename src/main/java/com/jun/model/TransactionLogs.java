package com.jun.model;

import java.util.Objects;

public class TransactionLogs {
	private int tableId;
	private String transactionTime;
	private int userId;
	private String action;
	public TransactionLogs() {
		
	}
	public TransactionLogs(int tableId, String transactionTime, int userId, String action) {
		super();
		this.tableId = tableId;
		this.transactionTime = transactionTime;
		this.userId = userId;
		this.action = action;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@Override
	public int hashCode() {
		return Objects.hash(action, tableId, transactionTime, userId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionLogs other = (TransactionLogs) obj;
		return Objects.equals(action, other.action) && tableId == other.tableId
				&& Objects.equals(transactionTime, other.transactionTime) && userId == other.userId;
	}
	@Override
	public String toString() {
		return "TransactionLogs [tableId=" + tableId + ", transactionTime=" + transactionTime + ", userId=" + userId
				+ ", action=" + action + "]";
	}

}
