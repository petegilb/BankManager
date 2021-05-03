public class LoanReceipt {
	String username;
	double amount;
	String currency;

	public LoanReceipt(String username, double amount, String currency) {
		this.username = username;
		this.amount = amount;
		this.currency = currency;
	}

	public String getUsername() {
		return username;
	}

	public double getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}
}