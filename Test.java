import java.util.*;

public class Test {

	/*
	 * Test UserManagement functionality
	 */
	public static void testUserManagement() {
		User user;
		BaseBank bank = new BaseBank();

		// admin login success
		user = bank.login("admin", "12345");
		assert(user.toString().equals("admin"));

		// admin login fail
		user = bank.login("admin", "123456");
		assert(user == null);

		// wrong username
		user = bank.login("adminn", "12345");
		assert(user == null);

		// create normal user
		user = bank.createUser("user1", "password1");
		assert(user.toString().equals("user1"));

		// login normal user
		user = bank.login("user1", "password1");
		assert(user.toString().equals("user1"));
	}

	/*
	 * Test account functionality
	 */
	public static void testAccount() {
		BaseBank bank = new BaseBank();
		NormalUser user = new NormalUser("user1", "password1");

		Account checking = bank.createAccount(user, AccountType.CHECKING);
		checking.deposit(1000);

		HashMap<Integer, Account> history = bank.getAccounts();
		for (Account account : history.values()) {
			for (Transaction t : account.getTransactions()) {
				System.out.println(t);
			}
		}
	}

	public static void main(String[] args) {
		// Test.testUserManagement();
		// Test.testAccount();
	}
}
