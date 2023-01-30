package elements;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * class where market operations occur
 * 
 * @author Beyzanur Bektan
 *
 */
public class Market {
	/**
	 * priority queue for all selling orders
	 */
	private PriorityQueue<SellingOrder> sellingOrders = new PriorityQueue<SellingOrder>();
	/**
	 * priority queue for all buying orders
	 */
	private PriorityQueue<BuyingOrder> buyingOrders = new PriorityQueue<BuyingOrder>();
	/**
	 * list for all successful transactions
	 */
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	private int fee;
	
	/**
	 * Market constructor method of market
	 * 
	 * @param fee commission the market gets from the transaction per thousand
	 */
	public Market(int fee) {
		this.fee = fee;
	}
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}


	public PriorityQueue<SellingOrder> getSellingOrders() {
		return sellingOrders;
	}

	public PriorityQueue<BuyingOrder> getBuyingOrders() {
		return buyingOrders;
	}
	
	/**
	 * giveSellOrder adds order to queue of selling orders
	 * 
	 * @param order order that will be added to queue
	 */
	public void giveSellOrder(SellingOrder order) {
		sellingOrders.add(order);
	}
	
	/**
	 * giveBuyOrder adds order to queue of buying orders
	 * 
	 * @param order order that will be added to queue
	 */
	public void giveBuyOrder(BuyingOrder order) {
		buyingOrders.add(order);
	}

	/**
	 * makeOpenMarketOperation market compensates orders in order to set the price of PQoin to the given price
	 * 
	 * @param price price given by market
	 * @param traders list for all traders
	 */
	public void makeOpenMarketOperation(double price, ArrayList<Trader> traders) {
		if ((!this.buyingOrders.isEmpty()) && (!this.sellingOrders.isEmpty())) {
		if (buyingOrders.peek().getPrice() >= price) {
			while ((this.buyingOrders.size()!=0) && (this.sellingOrders.size()!=0) && (buyingOrders.peek().getPrice() >= price)) {
				giveSellOrder(new SellingOrder(0, buyingOrders.peek().getAmount(), buyingOrders.peek().getPrice()));
				if ((this.buyingOrders.size()!=0) && (this.sellingOrders.size()!=0)) {
					checkTransactions(traders);
				}
			}
		}
		if (sellingOrders.peek().getPrice() <= price) {
			while ((this.buyingOrders.size()!=0) && (this.sellingOrders.size()!=0) && (sellingOrders.peek().getPrice() <= price)) {
				giveBuyOrder(new BuyingOrder(0, sellingOrders.peek().getAmount(), sellingOrders.peek().getPrice()));
				if ((!this.buyingOrders.isEmpty()) && (!this.sellingOrders.isEmpty())) {
					checkTransactions(traders);
				}
			}
		}
		}
	}
	
	/**
	 * checkTransactions the method where controls and transaction operations occur
	 * 
	 * @param traders list for all traders
	 */
	public void checkTransactions(ArrayList<Trader> traders) {
		if ((this.buyingOrders.size()!=0) && (this.sellingOrders.size()!=0)) {
		while ((this.buyingOrders.size()!=0) && (this.sellingOrders.size()!=0) && (sellingOrders.peek().getPrice() <= buyingOrders.peek().getPrice())) {
			if (sellingOrders.peek().getAmount() == buyingOrders.peek().getAmount()) {
				if (sellingOrders.peek().getPrice() == buyingOrders.peek().getPrice()) {
					transactions.add(new Transaction(sellingOrders.peek(), buyingOrders.peek()));
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setBlockedCoins(traders.get(sellingOrders.peek().getTraderID()).getWallet().getBlockedCoins() - sellingOrders.peek().getAmount());
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setDollars(traders.get(sellingOrders.peek().getTraderID()).getWallet().getDollars() + sellingOrders.peek().getAmount()*sellingOrders.peek().getPrice()*(1-(double)this.fee/1000));
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setBlockedDollars(traders.get(buyingOrders.peek().getTraderID()).getWallet().getBlockedDollars() - buyingOrders.peek().getAmount()*buyingOrders.peek().getPrice());
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setCoins(traders.get(buyingOrders.peek().getTraderID()).getWallet().getCoins() + sellingOrders.peek().getAmount());
					sellingOrders.poll();
					buyingOrders.poll();
				}
				
				else {
					transactions.add(new Transaction(sellingOrders.peek(), buyingOrders.peek()));
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setBlockedCoins(traders.get(sellingOrders.peek().getTraderID()).getWallet().getBlockedCoins() - sellingOrders.peek().getAmount());
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setDollars(traders.get(sellingOrders.peek().getTraderID()).getWallet().getDollars() + sellingOrders.peek().getAmount()*sellingOrders.peek().getPrice()*(1-(double)this.fee/1000));
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setBlockedDollars(traders.get(buyingOrders.peek().getTraderID()).getWallet().getBlockedDollars() - buyingOrders.peek().getAmount()*buyingOrders.peek().getPrice());
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setDollars(traders.get(buyingOrders.peek().getTraderID()).getWallet().getDollars() + (buyingOrders.peek().getPrice() - sellingOrders.peek().getPrice())* buyingOrders.peek().getAmount());
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setCoins(traders.get(buyingOrders.peek().getTraderID()).getWallet().getCoins() + sellingOrders.peek().getAmount());
					sellingOrders.poll();
					buyingOrders.poll();
				}
			}
			
			else if (sellingOrders.peek().getAmount() > buyingOrders.peek().getAmount()) {
				if (sellingOrders.peek().getPrice() == buyingOrders.peek().getPrice()) {
					transactions.add(new Transaction(sellingOrders.peek(), buyingOrders.peek()));
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setBlockedCoins(traders.get(sellingOrders.peek().getTraderID()).getWallet().getBlockedCoins() - buyingOrders.peek().getAmount());
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setDollars(traders.get(sellingOrders.peek().getTraderID()).getWallet().getDollars() + buyingOrders.peek().getAmount()*sellingOrders.peek().getPrice()*(1-(double)this.fee/1000));
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setBlockedDollars(traders.get(buyingOrders.peek().getTraderID()).getWallet().getBlockedDollars() - buyingOrders.peek().getAmount()*buyingOrders.peek().getPrice());
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setCoins(traders.get(buyingOrders.peek().getTraderID()).getWallet().getCoins() + buyingOrders.peek().getAmount());
					sellingOrders.peek().setAmount(sellingOrders.peek().getAmount() - buyingOrders.peek().getAmount());
					buyingOrders.poll();
				}
				
				else {
					transactions.add(new Transaction(sellingOrders.peek(), buyingOrders.peek()));
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setBlockedCoins(traders.get(sellingOrders.peek().getTraderID()).getWallet().getBlockedCoins() - buyingOrders.peek().getAmount());
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setDollars(traders.get(sellingOrders.peek().getTraderID()).getWallet().getDollars() + buyingOrders.peek().getAmount()*sellingOrders.peek().getPrice()*(1-(double)this.fee/1000));
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setBlockedDollars(traders.get(buyingOrders.peek().getTraderID()).getWallet().getBlockedDollars() - buyingOrders.peek().getAmount()*buyingOrders.peek().getPrice());
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setDollars(traders.get(buyingOrders.peek().getTraderID()).getWallet().getDollars() + (buyingOrders.peek().getPrice() - sellingOrders.peek().getPrice())* buyingOrders.peek().getAmount());
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setCoins(traders.get(buyingOrders.peek().getTraderID()).getWallet().getCoins() + buyingOrders.peek().getAmount());
					sellingOrders.peek().setAmount(sellingOrders.peek().getAmount() - buyingOrders.peek().getAmount());
					buyingOrders.poll();
				}
				
			}
			
			else if (sellingOrders.peek().getAmount() < buyingOrders.peek().getAmount()) {
				if (sellingOrders.peek().getPrice() == buyingOrders.peek().getPrice()) {
					transactions.add(new Transaction(sellingOrders.peek(), buyingOrders.peek()));
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setBlockedCoins(traders.get(sellingOrders.peek().getTraderID()).getWallet().getBlockedCoins() - sellingOrders.peek().getAmount());
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setDollars(traders.get(sellingOrders.peek().getTraderID()).getWallet().getDollars() + sellingOrders.peek().getAmount()*sellingOrders.peek().getPrice()*(1-(double)this.fee/1000));
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setBlockedDollars(traders.get(buyingOrders.peek().getTraderID()).getWallet().getBlockedDollars() - sellingOrders.peek().getAmount()*buyingOrders.peek().getPrice());
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setCoins(traders.get(buyingOrders.peek().getTraderID()).getWallet().getCoins() + sellingOrders.peek().getAmount());
					buyingOrders.peek().setAmount(buyingOrders.peek().getAmount() - sellingOrders.peek().getAmount());
					sellingOrders.poll();
				}
				
				else {
					transactions.add(new Transaction(sellingOrders.peek(), buyingOrders.peek()));
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setBlockedCoins(traders.get(sellingOrders.peek().getTraderID()).getWallet().getBlockedCoins() - sellingOrders.peek().getAmount());
					traders.get(sellingOrders.peek().getTraderID()).getWallet().setDollars(traders.get(sellingOrders.peek().getTraderID()).getWallet().getDollars() + sellingOrders.peek().getAmount()*sellingOrders.peek().getPrice()*(1-(double)this.fee/1000));
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setBlockedDollars(traders.get(buyingOrders.peek().getTraderID()).getWallet().getBlockedDollars() - sellingOrders.peek().getAmount()*buyingOrders.peek().getPrice());
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setDollars(traders.get(buyingOrders.peek().getTraderID()).getWallet().getDollars() + (buyingOrders.peek().getPrice() - sellingOrders.peek().getPrice())* sellingOrders.peek().getAmount());
					traders.get(buyingOrders.peek().getTraderID()).getWallet().setCoins(traders.get(buyingOrders.peek().getTraderID()).getWallet().getCoins() + sellingOrders.peek().getAmount());
					buyingOrders.peek().setAmount(buyingOrders.peek().getAmount() - sellingOrders.peek().getAmount());
					sellingOrders.poll();
				}
			}
		}
	}
	}
	

}
