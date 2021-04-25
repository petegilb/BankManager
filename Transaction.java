public class Transaction {
	private String username;
	private TransactionType type;
	private int amount;
	private boolean success;

	public Transaction(String username, TransactionType type, int amount, boolean success) {
		this.username = username;
		this.type = type;
		this.amount = amount;
		this.success = success;
	}

	public String toString() {
		return "User: " + username + ", Type: " + type.name() + 
		 ", Amount: " + amount + ", Success:" + success;
	}
}