import java.util.*;

public class Test {

	public static void testLoan() {
		BaseBank bank = BaseBank.getBank();

		// admin login success
		BankManager manager = (BankManager) bank.login("admin", "12345");
		assert(manager.toString().equals("admin"));

		manager.setLoanInterestRate(0.1);

		// create normal user
		NormalUser user = (NormalUser) bank.createUser("user3", "password3");
		assert(user.toString().equals("user3"));

		// create account
		Account checking = bank.createAccount(user, AccountType.CHECKING);
		checking.deposit(1000, "USD");

		// get loan
		LoanReceipt loan = user.borrowLoan(checking, 500, "USD");

		// pay loan back
		boolean success =  bank.getStorage().getLM().payBack(user, checking, loan);

		System.out.println(checking.getBalance("USD"));
		System.out.println();
	}

	public static void testStock() {
		BaseBank bank = BaseBank.getBank();

		// admin login success
		BankManager manager = (BankManager) bank.login("admin", "12345");
		assert(manager.toString().equals("admin"));

		manager.addStock("APPL", 120);

		// create normal user
		NormalUser user = (NormalUser) bank.createUser("user2", "password2");
		assert(user.toString().equals("user2"));

		// create and desosit to account
		InvestmentAccount investment = (InvestmentAccount) bank.createAccount(user, AccountType.INVESTMENT);
		assert(investment != null);
		investment.deposit(1000, "USD");

		// buy stock
		boolean buySuccess = investment.buyStock("APPL", 5);
		assert(buySuccess);

		// change stock price
		manager.setStockPrice("APPL", 130);

		// sell stock
		boolean sellSuccess = investment.sellStock("APPL", 3);
		assert(sellSuccess);

		// check unrealized gain
		HashMap<String, Double> unrealizedGain = investment.getUnrealizedGain();
		for (Map.Entry<String, Double> entry : unrealizedGain.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		// check realized gain
		HashMap<String, Double> realizedGain = investment.getRealizedGain();
		for (Map.Entry<String, Double> entry : realizedGain.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		System.out.println();
	}

	/*
	 * Test UserManagement functionality
	 */
	public static void testUserManagement() {
		User user;
		BaseBank bank = BaseBank.getBank();

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
		System.out.println();
	}

	/*
	 * Test account functionality
	 */
	public static void testAccount() {
		BaseBank bank = BaseBank.getBank();
		NormalUser user = new NormalUser("user1", "password1");

		Account checking = bank.createAccount(user, AccountType.CHECKING);
		checking.deposit(1000, "USD");

		HashMap<Integer, Account> history = bank.getAccounts();
		for (Account account : history.values()) {
			for (Transaction t : account.getTransactions()) {
				System.out.println(t);
			}
		}

		System.out.println();
	}

	public static void testStorage(){
		NormalUser user;
		BaseBank bank = BaseBank.getBank();
		bank.createUser("user5", "password5");
		user = (NormalUser) bank.login("user5", "password5");
		bank.createUser("user6", "pswrd");
		bank.createUser("user7", "joemama");

		//check making accounts for users
		Account checking = bank.createAccount(user, AccountType.CHECKING);
		checking.deposit(1000, "USD");
		user = (NormalUser) bank.login("user7", "joemama");
		System.out.println(user.getUsername());
		bank.createAccount(user, AccountType.SAVING);
		LoanReceipt loan = user.borrowLoan(checking, 500, "USD");

		user = (NormalUser) bank.getStorage().getUM().getUsers().get("user6");
		InvestmentAccount investTest = (InvestmentAccount) bank.createAccount(user, AccountType.INVESTMENT);
		investTest.deposit(10000, "USD");
		investTest.buyStock("APPL", 5);

		bank.AddExchangeRate("JPN", 5.0);

		//write to storage
		bank.getStorage().writeStorage();

		//now test reading from what we just created
		bank.getStorage().readStorage();
		System.out.println(bank.getStorage().getUM().getUsers());
		System.out.println(bank.getStorage().getUM().getUsers().get("user6").getPassword());
		NormalUser testUser = (NormalUser) bank.getStorage().getUM().getUsers().get("user5");
		System.out.println(testUser.getAccounts().get(0).getBalance("USD"));

		testUser = (NormalUser) bank.getStorage().getUM().getUsers().get("user6");
		investTest = (InvestmentAccount) testUser.getAccounts().get(0);
		System.out.println(investTest.getHoldStocks().get("APPL").get(0).getName());

		System.out.println(bank.getStorage().getSM().getStocks());

		testUser = (NormalUser) bank.getStorage().getUM().getUsers().get("user2");
		System.out.println(testUser.getAccounts().get(0).getTransactions());

		//loan tester
		System.out.println(bank.getStorage().getLM().getLoanReceipts().get("user7").get(0).getAmount());

		//test currency
		System.out.println(bank.getExchangeRate("JPN"));


		System.out.println();
	}

	public static void testGUI() {
		Login login = new Login();
	}

}
