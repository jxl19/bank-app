package com.jun.model;

import java.util.Objects;

public class PendingTransfer {
	
	private String fromAccountId;
	private String toAccountId;
	private double amount;
	private boolean pending;
	private int transferId;
	public PendingTransfer(String fromAccountId, String toAccountId, double amount, boolean pending, int transferId) {
		super();
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
		this.pending = pending;
		this.transferId = transferId;
	}
	public String getFromAccountId() {
		return fromAccountId;
	}
	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	public String getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public boolean isPending() {
		return pending;
	}
	public void setPending(boolean pending) {
		this.pending = pending;
	}
	public int getTransferId() {
		return transferId;
	}
	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, fromAccountId, pending, toAccountId, transferId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PendingTransfer other = (PendingTransfer) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(fromAccountId, other.fromAccountId) && pending == other.pending
				&& Objects.equals(toAccountId, other.toAccountId) && transferId == other.transferId;
	}
	@Override
	public String toString() {
		return "PendingTransfer [fromAccountId=" + fromAccountId + ", toAccountId=" + toAccountId + ", amount=" + amount
				+ ", pending=" + pending + ", transferId=" + transferId + "]";
	}
	
}
