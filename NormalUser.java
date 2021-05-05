import java.util.*;

public class NormalUser extends User {
	private List<Account> accounts;
	private List<LoanReceipt> loans;

	public NormalUser(String username, String password) {
		super(username, password);
		this.accounts = new ArrayList<Account>();
		type = UserType.NORMAL;
		loans = new ArrayList<LoanReceipt>();
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	public List<LoanReceipt> getLoans(){
		return loans;
	}

	public List<Account> getAccounts(){
		return accounts;
	}

	public LoanReceipt borrowLoan(Account account, double amount, String currency) {
		LoanReceipt loan = BaseBank.getBank().getStorage().getLM().getLoan(this, amount, currency);
		loans.add(loan);
		account.deposit(amount, currency);
		return loan;
	}

	public boolean payBackLoan(LoanReceipt loan, Account account) {
		boolean success = BaseBank.getBank().getStorage().getLM().payBack(this, account, loan);

		if (success) {
			loans.remove(loan);
		}

		return success;
	}
}
