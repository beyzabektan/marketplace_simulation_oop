package elements;

/**
 * class where buying orders are created
 * 
 * @author Beyzanur Bektan
 *
 */
public class BuyingOrder extends Order implements Comparable<BuyingOrder>{
	
	/**
	 * BuyingOrder constructor method of buying order
	 * 
	 * @param traderID trader id of the buyer
	 * @param amount amount of coins to buy
	 * @param price price of a coin in the order
	 */
	public BuyingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
	}
	
	/**
	 * comparing prices of buying orders
	 */
	@Override
	public int compareTo(BuyingOrder o) {
		// TODO Auto-generated method stub
		if(this.price > o.price) {
            return -1;
        } 
		else if (this.price < o.price) {
            return 1;
        } 
		else {
			if(this.amount > o.amount) {
	            return -1;
	        } 
			else if (this.amount < o.amount) {
	            return 1;
	        }
			else {
				if(this.traderID > o.traderID) {
		            return 1;
		        } 
				else {
		            return -1;
		        }
			}
        }
	}

}
