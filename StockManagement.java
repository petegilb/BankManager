import java.util.*;

public class StockManagement {
	private HashMap<String, Stock> stocks;

	public StockManagement() {
		this.stocks = new HashMap<String, Stock>();
	}

	public void addStock(Stock stock) {
		stocks.put(stock.getName(), stock);
	}

	public void setStockPrice(String name, double price) {
		stocks.get(name).setPrice(price);
	}

	public double getStockPrice(String name) {
		return stocks.get(name).getPrice();
	}

	public HashMap<String, Stock> getStocks() {
		return stocks;
	}

	public List<Share> buyStock(InvestmentAccount account, String name, int numShares) {
		double price = this.getStockPrice(name) * numShares;
		Transaction t = account.withdraw(price, "USD");

		List<Share> shares = new ArrayList<Share>();

		if (t.isSuccess()) {
			for (int i = 0; i < numShares; i++) {
				shares.add(new Share(name, this.getStockPrice(name)));
			}	
		}

		return shares;
	}

	public void sellStock(InvestmentAccount account, List<Share> shares) {
		double totalPrice = 0;

		for (Share share : shares) {
			double price = this.getStockPrice(share.getName());
			totalPrice += price;
			share.markSold(price);
		}

		Transaction t = account.deposit(totalPrice, "USD");
	}
}
