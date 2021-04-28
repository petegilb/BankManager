import java.util.*;

public class BankManager extends User {
	private BaseBank bank;

	public BankManager(String username, String password, BaseBank bank) {
		super(username, password);
		this.bank = bank;
		type = UserType.ADMIN;
	}

	public HashMap<Integer, Account> getAccounts() {
		return bank.getAccounts();
	}
}
