import java.util.*;

public class NormalUser extends User {
	private List<Account> accounts;

	public NormalUser(String username, String password) {
		super(username, password);
		this.accounts = new ArrayList<Account>();
		type = UserType.NORMAL;
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	public List<Account> getAccounts(){
		return accounts;
	}
}
