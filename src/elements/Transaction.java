package elements;

/**
 * class where transactions are created
 * 
 * @author Beyzanur Bektan
 *
 */
public class Transaction {
	private SellingOrder sellingOrder;
	private BuyingOrder buyingOrder;
	
	/**
	 * Transaction constructor method of transaction
	 * 
	 * @param sellingOrder selling order of transaction
	 * @param buyingOrder buying order of transaction
	 */
	public Transaction(SellingOrder sellingOrder, BuyingOrder buyingOrder) {
		this.sellingOrder = sellingOrder;
		this.buyingOrder = buyingOrder;
	}

}
