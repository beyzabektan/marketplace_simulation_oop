package executable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.io.PrintStream;
import java.util.Scanner;


import java.io.File;
import java.io.FileNotFoundException;

import elements.BuyingOrder;
import elements.Market;
import elements.SellingOrder;
import elements.Trader;

/**
 * Main class of the project where other classes methods are called
 * 
 * @author Beyzanur Bektan
 *
 */
public class Main {
	/**
	 * myRandom to determine reward rates randomly
	 */
	public static Random myRandom;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
	
	ArrayList<Trader> traders = new ArrayList<Trader>();
	
	int seed = in.nextInt();
	myRandom = new Random(seed);
	
	int fee = in.nextInt();
	int numberOfUsers = in.nextInt();
	int numberOfQueries = in.nextInt();
	int numberOfInvalidQueries = 0;
	Market themarket = new Market(fee);
	
	for (int i = 0; i < numberOfUsers; i++) {
		traders.add(new Trader(in.nextDouble(), in.nextDouble()));
		Trader.numberOfUsers ++;
	}
	
	for (int i = 0; i < numberOfQueries; i++) {
		int action = in.nextInt();
		if (action == 10) {
			int traderid = in.nextInt();
			double price = in.nextDouble();
			double amount = in.nextDouble();
			if ((traders.get(traderid).buy(amount, price, themarket)) == 1) {
				BuyingOrder order = new BuyingOrder(traderid, amount, price);
				themarket.giveBuyOrder(order);
				traders.get(traderid).getWallet().setBlockedDollars(traders.get(traderid).getWallet().getBlockedDollars() + (amount*price));
				traders.get(traderid).getWallet().setDollars(traders.get(traderid).getWallet().getDollars() - (amount*price));
				
			}
			else {
				numberOfInvalidQueries ++;
			}
			if ((!themarket.getBuyingOrders().isEmpty()) && (!themarket.getSellingOrders().isEmpty())) {
				themarket.checkTransactions(traders);
			}
		}
		
		if (action == 11) {
			int traderid = in.nextInt();
			double amount = in.nextDouble();
			if (themarket.getSellingOrders().isEmpty()) {
				numberOfInvalidQueries ++;
			}
			if ((traders.get(traderid).buy(amount, themarket.getSellingOrders().peek().getPrice(), themarket)) == 0){
				numberOfInvalidQueries ++;
			}
			else {
				BuyingOrder order = new BuyingOrder(traderid, amount, themarket.getSellingOrders().peek().getPrice());
				themarket.giveBuyOrder(order);
				traders.get(traderid).getWallet().setBlockedDollars(traders.get(traderid).getWallet().getBlockedDollars() + (amount*themarket.getSellingOrders().peek().getPrice()));
				traders.get(traderid).getWallet().setDollars(traders.get(traderid).getWallet().getDollars() - (amount*themarket.getSellingOrders().peek().getPrice()));
			}
			if ((!themarket.getBuyingOrders().isEmpty()) && (!themarket.getSellingOrders().isEmpty())) {
				themarket.checkTransactions(traders);
			}
		}
		
		if (action == 20) {
			int traderid = in.nextInt();
			double price = in.nextDouble();
			double amount = in.nextDouble();
			if ((traders.get(traderid).sell(amount, price, themarket)) == 1) {
				SellingOrder order = new SellingOrder(traderid, amount, price);
				themarket.giveSellOrder(order);
				traders.get(traderid).getWallet().setBlockedCoins(traders.get(traderid).getWallet().getBlockedCoins() + amount);
				traders.get(traderid).getWallet().setCoins(traders.get(traderid).getWallet().getCoins() - amount);
			}
			else {
				numberOfInvalidQueries ++;
			}
			if ((!themarket.getBuyingOrders().isEmpty()) && (!themarket.getSellingOrders().isEmpty())) {
				themarket.checkTransactions(traders);
			}
		}
		
		if (action == 21) {
			int traderid = in.nextInt();
			double amount = in.nextDouble();
			if (themarket.getBuyingOrders().isEmpty()) {
				numberOfInvalidQueries ++;
			}
			if ((traders.get(traderid).sell(amount, themarket.getBuyingOrders().peek().getPrice(), themarket)) == 0){
				numberOfInvalidQueries ++;
			}
			else {
				SellingOrder order = new SellingOrder(traderid, amount, themarket.getBuyingOrders().peek().getPrice());
				themarket.giveSellOrder(order);
				traders.get(traderid).getWallet().setBlockedCoins(traders.get(traderid).getWallet().getBlockedCoins() + amount);
				traders.get(traderid).getWallet().setCoins(traders.get(traderid).getWallet().getCoins() - amount);
			}
			if ((!themarket.getBuyingOrders().isEmpty()) && (!themarket.getSellingOrders().isEmpty())) {
				themarket.checkTransactions(traders);
			}
		}
		
		if (action == 3) {
			int traderid = in.nextInt();
			double amount = in.nextDouble();
			traders.get(traderid).getWallet().setDollars(traders.get(traderid).getWallet().getDollars() + amount);
		}
		
		if (action == 4) {
			int traderid = in.nextInt();
			double amount = in.nextDouble();
			if (amount <= traders.get(traderid).getWallet().getDollars()) {
				traders.get(traderid).getWallet().setDollars(traders.get(traderid).getWallet().getDollars() - amount);
			}
			else {
				numberOfInvalidQueries ++;
			}
		}
		
		if (action == 5) {
			int traderid = in.nextInt();
			out.print("Trader " + traderid + ": ");
			out.print(String.format("%.5f",(traders.get(traderid).getWallet().getDollars() + traders.get(traderid).getWallet().getBlockedDollars())) + "$ ");
			out.println(String.format("%.5f",(traders.get(traderid).getWallet().getCoins() + traders.get(traderid).getWallet().getBlockedCoins())) + "PQ");
			}
		
		if (action == 777) {
			for (int k = 0; k < traders.size(); k ++) {
				traders.get(k).getWallet().setCoins(traders.get(k).getWallet().getCoins() + myRandom.nextDouble()*10);
			}
		}
		
		if (action == 666) {
			double price = in.nextDouble();
			if ((!themarket.getBuyingOrders().isEmpty()) && (!themarket.getSellingOrders().isEmpty())) {
				themarket.makeOpenMarketOperation(price, traders);
			}
		}
		
		if (action == 500) {
			double totalinbuyingpq = 0;
			double totalinsellingpq = 0;
			if((!themarket.getSellingOrders().isEmpty()) && (!themarket.getSellingOrders().isEmpty())) {
				Iterator<SellingOrder> itrs = themarket.getSellingOrders().iterator();
				Iterator<BuyingOrder> itrb = themarket.getBuyingOrders().iterator();
				while (itrb.hasNext()) {
					BuyingOrder b = itrb.next();
					totalinbuyingpq += (b.getPrice() * b.getAmount());
			    }
				while (itrs.hasNext()) {
					SellingOrder s = itrs.next();
					totalinsellingpq += (s.getAmount());
			    }
				out.println("Current market size: " + String.format("%.5f",totalinbuyingpq) + " " + String.format("%.5f",totalinsellingpq));
			}
		}
		
		if (action == 501) {
			out.println("Number of successful transactions: " + themarket.getTransactions().size());
		}
		
		if (action == 502) {
			out.println("Number of invalid queries: " + numberOfInvalidQueries);
		}
		
		if (action == 505) {
			double cpbuying = 0;
			double cpselling = 0;
			double average = 0;
			if (!themarket.getBuyingOrders().isEmpty()) {
				cpbuying += themarket.getBuyingOrders().peek().getPrice();
			}
			if (!themarket.getSellingOrders().isEmpty()) {
				cpselling += themarket.getSellingOrders().peek().getPrice();
			}
			if ((cpbuying == 0) && (cpselling > 0)) {
				average += cpselling;
			}
			if ((cpbuying > 0) && (cpselling == 0)) {
				average += cpbuying;
			}
			if ((cpbuying == 0) && (cpselling == 0)) {
				average += 0;
			}
			if ((cpbuying > 0) && (cpselling) > 0) {
				average += (cpbuying + cpselling)/2;
			}
			out.println("Current prices: " + String.format("%.5f",cpbuying) + " " + String.format("%.5f",cpselling) + " " + String.format("%.5f",average));
		}
		
		if (action == 555) {	
			for (int x = 0; x < traders.size(); x++) {
				out.print("Trader " + x + ": ");
				out.print(String.format("%.5f",(traders.get(x).getWallet().getDollars() + traders.get(x).getWallet().getBlockedDollars())) + "$ ");
				out.println(String.format("%.5f",(traders.get(x).getWallet().getCoins() + traders.get(x).getWallet().getBlockedCoins())) + "PQ");
			}
		}
	}
	
	}
}


