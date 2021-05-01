public class Transaction {
	private String username;
	private TransactionType type;
	private double amount;
	private String currency;
	private boolean success;

	public Transaction(String username, TransactionType type, double amount, String currency, boolean success) {
		this.username = username;
		this.type = type;
		this.amount = amount;
		this.currency = currency;
		this.success = success;
	}

	public String toString() {
		return "User: " + username + ", Type: " + type.name() + 
		 ", Amount: " + amount + ", Currency: " + currency + ", Success:" + success;
	}
}