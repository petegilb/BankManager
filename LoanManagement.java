import java.util.*;

public class LoanManagement {
	private HashMap<String, List<LoanReceipt>> loanReceipts;
	private double rate;

	public LoanManagement() {
		this.loanReceipts = new HashMap<String, List<LoanReceipt>>();
		this.rate = 0.05; // the default loan interest rate is 0.05, but can be change by the bank manager
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public LoanReceipt getLoan(NormalUser user, double amount, String currency) {
		String username = user.getUsername();

		if (!loanReceipts.containsKey(username)) {
			loanReceipts.put(username, new ArrayList<LoanReceipt>());
		}

		LoanReceipt loan = new LoanReceipt(username, amount + amount * rate, currency);
		loanReceipts.get(username).add(loan);

		return loan;
	}

	public boolean payBack(NormalUser user, Account account, LoanReceipt loan) {
		Transaction transaction = account.withdraw(loan.getAmount(), loan.getCurrency());

		boolean success = transaction.isSuccess();

		if (success) {
			loanReceipts.get(user.getUsername()).remove(loan);
		}

		return success;
	}

	public HashMap<String, List<LoanReceipt>> getLoanReceipts(){
		return loanReceipts;
	}

	public double getRate(){
		return rate;
	}

	public void addLoanReceipt(String username, ArrayList<LoanReceipt> toAdd){
		loanReceipts.put(username, toAdd);
	}
}
