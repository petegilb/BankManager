import java.util.*;

public class InvestmentAccount extends Account {
	private HashMap<String, List<Share>> holdStocks;
	private HashMap<String, List<Share>> soldStocks;

	public InvestmentAccount(int id, String username) {
		super(id, username);
		this.type = AccountType.INVESTMENT;
		this.holdStocks = new HashMap<String, List<Share>>();
		this.soldStocks = new HashMap<String, List<Share>>();
	}

	public boolean buyStock(String name, int numShares) {
		List<Share> shares = BaseBank.getBank().getStorage().getSM().buyStock(this, name, numShares);

		if (!holdStocks.containsKey(name)) {
			holdStocks.put(name, new ArrayList<Share>());
		}

		holdStocks.get(name).addAll(shares);

		return shares.size() > 0;
	}

	public boolean sellStock(String name, int numShares) {
		List<Share> allShares = holdStocks.get(name);

		if (allShares.size() < numShares) {
			return false;
		}

		List<Share> shares = allShares.subList(0, numShares);
		holdStocks.put(name, allShares.subList(numShares, allShares.size()));

		BaseBank.getBank().getStorage().getSM().sellStock(this, shares);

		if (!soldStocks.containsKey(name)) {
			soldStocks.put(name, new ArrayList<Share>());
		}

		soldStocks.get(name).addAll(shares);

		return true;
	}

	public HashMap<String, Double> getUnrealizedGain() {
		StockManagement sm = BaseBank.getBank().getStorage().getSM();
		HashMap<String, Double> unrealizedGain = new HashMap<String, Double>();

		for (Map.Entry<String, List<Share>> entry : holdStocks.entrySet()) {
			String name = entry.getKey();
			double gain = 0;

			for (Share share : entry.getValue()) {
				gain += sm.getStockPrice(name) - share.getBoughtPrice();
			}

			unrealizedGain.put(name, gain);
		}

		return unrealizedGain;
	}

	public HashMap<String, Double> getRealizedGain() {
		StockManagement sm = BaseBank.getBank().getStorage().getSM();
		HashMap<String, Double> realizedGain = new HashMap<String, Double>();

		for (Map.Entry<String, List<Share>> entry : soldStocks.entrySet()) {
			String name = entry.getKey();
			double gain = 0;

			for (Share share : entry.getValue()) {
				gain += share.getSoldPrice() - share.getBoughtPrice();
			}

			realizedGain.put(name, gain);
		}

		return realizedGain;
	}

	public HashMap<String, List<Share>> getHoldStocks(){
		return holdStocks;
	}

	public HashMap<String, List<Share>> getSoldStocks(){
		return soldStocks;
	}

	public void setHoldStocks(HashMap<String, List<Share>> holdStocks){
		this.holdStocks = holdStocks;
	}
	public void setSoldStocks(HashMap<String, List<Share>> soldStocks){
		this.soldStocks = soldStocks;
	}
}
