import java.util.*;

public class Account {
	protected int id;
	protected int balance;
	protected String username;
	protected List<Transaction> transactions;
	protected AccountType type;

	public Account(int id, String username) {
		this.id = id;
		this.balance = 0;
		this.username = username;
		this.transactions = new ArrayList<Transaction>();
	}

	public Transaction deposit(int amount) {
		Transaction transaction;

		if (amount > 0) {
			this.balance += amount;
			transaction = new Transaction(username, TransactionType.DEPOSIT, amount, true);
		} else {
			transaction = new Transaction(username, TransactionType.DEPOSIT, amount, false);
		}

		transactions.add(transaction);

		return transaction;
	}

	public Transaction withdraw(int amount) {
		Transaction transaction;

		if (amount <= balance) {
			this.balance -= amount;
			transaction = new Transaction(username, TransactionType.WITHDRAW, amount, true);
		} else {
			transaction = new Transaction(username, TransactionType.WITHDRAW, amount, false);
		}

		transactions.add(transaction);

		return transaction;
	}

	public void addMoney(int toAdd){
		this.balance += toAdd;
	}

	//getters and setters

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public int getID(){
		return id;
	}

	public void setID(int id){
		this.id = id;
	}

	public int getBalance(){
		return balance;
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
