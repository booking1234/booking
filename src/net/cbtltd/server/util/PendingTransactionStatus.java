package net.cbtltd.server.util;

public enum PendingTransactionStatus {
	
	Active(1),
	Pending(2),
	Cleared(3),
	Failed(4),
	Deleted(5),
	Cancelled(6);
	
	private int status;
	PendingTransactionStatus(int status) {
		this.status = status;
	}
	
	public int status() {return status;}
	
}
