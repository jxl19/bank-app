package com.jun.ui;

public class TransactionMenu implements Menu{

	public String card_no;
	public String transaction_type;
//	public TransactionService transactionService;
	
	public TransactionMenu(String card_no, String transaction_type) {
		this.card_no = card_no;
		this.transaction_type = transaction_type;
//		this.transactionService = new TransactionService();
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		double bal = 1000.00;
		
	}

}
