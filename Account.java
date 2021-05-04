import java.util.*;

public class Account {
	protected int id;
	protected double balance;
	protected String username;
	protected List<Transaction> transactions;
	protected AccountType type;

	public Account(int id, String username) {
		this.id = id;
		this.balance = 0;
		this.username = username;
		this.transactions = new ArrayList<Transaction>();
	}

	public Transaction deposit(double amount, String currency) {
		Transaction transaction;
		double amountInUSD = amount / BaseBank.getBank().getExchangeRate(currency);

		if (amount > 0) {
			this.balance += amountInUSD;
			transaction = new Transaction(username, TransactionType.DEPOSIT, amount, currency, true);
		} else {
			transaction = new Transaction(username, TransactionType.DEPOSIT, amount, currency, false);
		}

		transactions.add(transaction);

		return transaction;
	}

	public Transaction withdraw(double amount, String currency) {
		Transaction transaction;

		double amountInUSD = amount / BaseBank.getBank().getExchangeRate(currency);

		if (amountInUSD <= balance) {
			this.balance -= amountInUSD;
			transaction = new Transaction(username, TransactionType.WITHDRAW, amount, currency, true);
		} else {
			transaction = new Transaction(username, TransactionType.WITHDRAW, amount, currency, false);
		}

		transactions.add(transaction);

		return transaction;
	}

	public void addMoney(int toAdd){
		this.balance += toAdd;
	}

	//getters and setters

	public void setTransactions(List<Transaction> transactions){
		this.transactions = transactions;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public int getID(){
		return id;
	}

	public void setID(int id){
		this.id = id;
	}

	public double getBalance(String currency){
		return balance * BaseBank.getBank().getExchangeRate(currency);
	}

	public void setBalance(int balance){
		this.balance = balance;
	}

	public String getUsername(){
		return username;
	}

	public AccountType getType(){
		return type;
	}



}
