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

		//write this to the storage
		//bank.getStorage().writeStorage();
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

	public static void testStorage(){
		NormalUser user;
		BaseBank bank = new BaseBank();
		bank.createUser("user1", "password1");
		user = (NormalUser) bank.login("user1", "password1");
		bank.createUser("user2", "pswrd");
		bank.createUser("user3", "joemama");

		//check making accounts for users
		Account checking = bank.createAccount(user, AccountType.CHECKING);
		checking.deposit(1000);

		bank.createAccount((NormalUser)bank.login("user3", "joemama"), AccountType.SAVING);

		//write to storage
		bank.getStorage().writeStorage();

		//now test reading from what we just created
		bank.getStorage().readStorage();
		System.out.println(bank.getStorage().getUM().getUsers());
		System.out.println(bank.getStorage().getUM().getUsers().get("user2").getPassword());
		NormalUser testUser = (NormalUser) bank.getStorage().getUM().getUsers().get("user1");
		System.out.println(testUser.getAccounts().get(0).getBalance());
	}

	public static void main(String[] args) {
		Test.testUserManagement();
		Test.testAccount();
		Test.testStorage();
	}
}
