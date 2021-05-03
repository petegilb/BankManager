public class Share {
	private String name;
	private double boughtPrice;
	private double soldPrice;
	private boolean sold;

	public Share(String name, double boughtPrice) {
		this.name = name;
		this.boughtPrice = boughtPrice;
		this.sold = false;
	}

	public String getName() {
		return name;
	}

	public double getBoughtPrice() {
		return boughtPrice;
	}

	public double getSoldPrice() {
		return soldPrice;
	}

	public void markSold(double soldPrice) {
		this.soldPrice = soldPrice;
		this.sold = sold;
	}
}