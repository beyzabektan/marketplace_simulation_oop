package elements;

/**
 * class for traders
 * 
 * @author Beyzanur Bektan
 *
 */
public class Trader {
	private int id;
	private Wallet wallet;
	
	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	
	/**
	 * Trader constructor method of trader
	 * 
	 * @param dollars total amount of dollars in the wallet of trader
	 * @param coins total amount of coins in the wallet of trader
	 */
	public Trader(double dollars, double coins) {
		this.wallet = new Wallet(dollars, coins);
	}
	
	/**
	 * sell method for checking if seller's total coins are enough for selling order
	 * 
	 * @param amount amount of coin to sell
	 * @param price price of a coin in the order
	 * @param market market
	 * @return returns 1 if order is possible   
	 */
	public int sell(double amount, double price, Market market) {
		if (amount > this.wallet.getCoins()) {
			return 0;
		}
		else {
			return 1;
			
		}
	}
	
	/**
	 * buy method for checking if buyer's total dollars are enough for buying order
	 * 
	 * @param amount amount of coin to buy
	 * @param price price of a coin in the order
	 * @param market market
	 * @return returns if order is possible
	 */
	public int buy(double amount, double price, Market market) {
		if ((amount * price) > this.wallet.getDollars()) {
			return 0;
		}
		else {
			return 1;
			
		}
	}
	
	/**
	 * total number of traders
	 */
	public static int numberOfUsers = 0;

}
