import java.util.*;

public class BaseBank {
	private BankStorage bs;
	private HashMap<Integer, Account> accounts;

	public BaseBank() {
		this.bs = new BankStorage(this);
		this.accounts = new HashMap<Integer, Account>();
	}

	/*
	 * Given the login info, return the corresponding if exists
	 */
	public User login(String username, String password) {
		return bs.getUM().login(username, password);
	}

	/*
	 * Create an user with given username and password
	 */
	public User createUser(String username, String password) {
		return bs.getUM().createUser(username, password);
	}

	/*
	 * Create an account for a given user and type
	 */
	public Account createAccount(NormalUser user, AccountType type) {
		Account account = null;

		Random rand = new Random();
		int id;
		do {
			id = rand.nextInt(1000000000);
		} while (accounts.containsKey(id));

		switch (type) {
			case CHECKING:
				account = new CheckingAccount(id, user.getUsername());
				break;
			case SAVING:
				account = new SavingAccount(id, user.getUsername());
				break;
			case INVESTMENT:
				account = new InvestmentAccount(id, user.getUsername());
			default:
				assert(account != null);
		}

		this.accounts.put(id, account);
		user.addAccount(account);

		return account;
	}

	public void addAccount(Account account){
		this.accounts.put(account.getID(), account);
	}

	public HashMap<Integer, Account> getAccounts() {
		return accounts;
	}

	public BankStorage getStorage(){
		return bs;
	}
}
