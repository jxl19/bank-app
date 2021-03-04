package com.jun.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.jun.exceptions.InvalidBalanceException;
import com.jun.model.PendingTransfer;
import com.jun.services.TransactionService;

public class PendingTransferMenu implements Menu{

	public TransactionService transactionService;
	public PendingTransferMenu() {
		this.transactionService = new TransactionService();
	}

	@Override
	public void display() {
		ArrayList<PendingTransfer> pendingTransfers = new ArrayList<>();
		int choice = 0;
		do {
			try {
				pendingTransfers = transactionService.checkPendingTransfer(MainMenu.loginId);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("=============================== Pending Transfers =================================");
			System.out.println("||                   Please select a pending transfer to review                  ||");
			System.out.println("||                   1.)Exit                                                     ||");

			//using as an exit condition
			if (pendingTransfers.size() > 0) {
				pendingTransfers.forEach(withCounter((i, pt) -> {
					String fromAccount = pt.getFromAccountId();
					double amount = pt.getAmount();
					System.out.println("||                   " + (i+2) + ".)" + "From account: " + fromAccount + " amount: " + amount + "           ||");
				}));
				System.out.println("===================================================================================");
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {};
			
			} else {
				System.out.println("There are no pending applications");
				choice = 1;
			}
			switch (choice) {
				case 1:
					break;
				default:
					int selected = 0;
					PendingTransfer currentAccount = pendingTransfers.get(choice - 2);
					System.out.println("============================= REVIEWING TRANSFER ==================================");
					System.out.println("||                       From account: " + currentAccount.getFromAccountId() + "                          ||");
					System.out.println("||                       To account: " + currentAccount.getToAccountId() + "                            ||");
					System.out.println("||                       Amount: " + currentAccount.getAmount() + "                                         ||");
					System.out.println("||                       1.) Exit                                                ||");
					System.out.println("||                       2.) Approve                                             ||");
					System.out.println("||                       3.) Deny                                                ||");
					System.out.println("===================================================================================");
					try {
						selected = Integer.parseInt(Menu.sc.nextLine());
					}catch(NumberFormatException e) {};
					
					switch(selected) {
						case 1:
							break;
						case 2:
						try {
							String approvedTransaction = transactionService.approveTransfer(currentAccount.getTransferId(), MainMenu.loginId);
							System.out.println(approvedTransaction);
							selected = 1;
							break;
						} catch (SQLException | InvalidBalanceException e) {
							System.out.println(e.getMessage());
						} 
							selected = 1;
							break;
						case 3:
							try {
								String approvedTransaction = transactionService.declineTransfer(currentAccount.getTransferId(), MainMenu.loginId);
								System.out.println(approvedTransaction);
								selected = 1;
								break;
							} catch (SQLException e) {
								System.out.println(e.getMessage());
							} 
							selected = 1;
							break;
					} while(selected != 1);
			}
		} while(choice != 1);
	}
	//create consumer to keep track of counter and pass to a biconsumer
	//returns a new lambda, uses atomicinteger to keep track of counter and increments on each new item
	private static <T>Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
	    AtomicInteger counter = new AtomicInteger(0);
	    return item -> consumer.accept(counter.getAndIncrement(), item);
	}

}
