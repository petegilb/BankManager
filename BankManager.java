import java.util.*;

public class BankManager extends User {

	public BankManager(String username, String password) {
		super(username, password);
		type = UserType.ADMIN;
	}

	public HashMap<Integer, Account> getAccounts() {
		return BaseBank.getBank().getAccounts();
	}

	public HashMap<String, Double> getCurrencies() { return BaseBank.getBank().getCurrencies(); }

	public void setLoanInterestRate(double rate) {
		BaseBank.getBank().getStorage().getLM().setRate(rate);
	}

	public void addStock(String name, double price) {
		Stock stock = new Stock(name, price);
		BaseBank.getBank().getStorage().getSM().addStock(stock);
	}

	public void setStockPrice(String name, double price) {
		BaseBank.getBank().getStorage().getSM().setStockPrice(name, price);
	}
}
