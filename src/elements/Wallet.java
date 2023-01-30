package elements;

/**
 * class where wallets are created
 * 
 * @author Beyzanur Bektan
 *
 */
public class Wallet {
	/**
	 * total amount of dollars in the wallet
	 */
	private double dollars;
	/**
	 * total amount of PQoins in the wallet
	 */
	private double coins;
	/**
	 * total amount of dollars in orders
	 */
	private double blockedDollars;
	/**
	 * total amount of PQoins in orders
	 */
	private double blockedCoins;
	
	public double getDollars() {
		return dollars;
	}

	public void setDollars(double dollars) {
		this.dollars = dollars;
	}


	public double getCoins() {
		return coins;
	}

	public void setCoins(double coins) {
		this.coins = coins;
	}

	public double getBlockedDollars() {
		return blockedDollars;
	}

	public void setBlockedDollars(double blockedDollars) {
		this.blockedDollars = blockedDollars;
	}

	public double getBlockedCoins() {
		return blockedCoins;
	}

	public void setBlockedCoins(double blockedCoins) {
		this.blockedCoins = blockedCoins;
	}
	
	/**
	 * Wallet constructor method of wallet
	 * 
	 * @param dollars total amount of dollars in the wallet
	 * @param coins total amount of PQoins in the wallet
	 */
	public Wallet(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;
	}

}
