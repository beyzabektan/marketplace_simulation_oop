package elements;

/**
 * class where selling orders are created
 * 
 * @author Beyzanur Bektan
 *
 */
public class SellingOrder extends Order implements Comparable<SellingOrder>{
	
	/**
	 * SellingOrder constructor method of selling order
	 * 
	 * @param traderID trader id of the seller
	 * @param amount amount of coins to sell
	 * @param price price of a coin in the order
	 */
	public SellingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
	}
	
	/**
	 * comparing prices of selling orders
	 */
	@Override
	public int compareTo(SellingOrder o) {
		// TODO Auto-generated method stub
		if(this.price > o.price) {
            return 1;
        } 
		else if (this.price < o.price) {
            return -1;
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
