package elements;

/**
 * class where orders are created
 * 
 * @author Beyzanur Bektan
 *
 */
public class Order {
	double amount;
	double price;
	int traderID;
	
	/**
	 * Order constructor method of order
	 * 
	 * @param traderID trader if of the buyer or seller
	 * @param amount amount of coins to buy or sell
	 * @param price price of a coin in the order
	 */
	public Order(int traderID, double amount, double price) {
		this.traderID = traderID;
		this.amount = amount;
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public int getTraderID() {
		return traderID;
	}
	
	
	

}


