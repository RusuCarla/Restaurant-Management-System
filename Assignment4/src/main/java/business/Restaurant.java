package business;

import java.awt.Menu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import data.FileWriter;
import data.RestaurantSerializator;

public class Restaurant extends Observable implements IRestaurantProcessing, java.io.Serializable{
	
	private ArrayList<MenuItem> menu;
	private HashMap<Order, ArrayList<MenuItem>> orders;
	private int orderID=0;

	public Restaurant() {
		menu = new ArrayList<MenuItem>();
		orders = new HashMap<Order, ArrayList<MenuItem>>();
	}

	public Object create(Object obj) {
		assert isWellFormed();
		BaseProduct baseProduct = new BaseProduct(null, 0);
		CompositeProduct compositeProduct = new CompositeProduct(null, null);
		Order order = new Order();
		String date = "";
		if (baseProduct.getClass() == obj.getClass()) {
			baseProduct = (BaseProduct) obj;
			this.menu.add(baseProduct);
			return baseProduct;
		} else if (compositeProduct.getClass() == obj.getClass()) {
			compositeProduct = (CompositeProduct) obj;
			List<MenuItem> list = new ArrayList<MenuItem>();
			CompositeProduct compositeProduct2 = new CompositeProduct(compositeProduct.getProduct(), list);
			for (MenuItem m: compositeProduct.getBaseProductList()) {
				MenuItem item = findMenuItemByName(m.getMenuItemName());
				//System.out.println(item.toString());
				compositeProduct2.addBaseProduct(item);
			}
			//this.menu.add(compositeProduct2);
			this.menu.add(compositeProduct);
			return compositeProduct;
		} else if (date.getClass() == obj.getClass()) {
			date = (String) obj;
			order = new Order();
			order.setDate(date);
			setOrderID(orders.size()+1);
			order.setOrderID(orderID);
			Random random = new Random();
			order.setTableID(random.nextInt(25));
			this.orders.put(order, new ArrayList<MenuItem>());
			return order;
		}
		assert isWellFormed();
		return null;
	}

	public void delete(Object obj) {
		assert isWellFormed();
		BaseProduct baseProduct = new BaseProduct(null, 0);
		CompositeProduct compositeProduct = new CompositeProduct(null, null);
		if (baseProduct.getClass() == obj.getClass()) {
			baseProduct = (BaseProduct) obj;
			this.menu.remove(baseProduct);
		} else if (compositeProduct.getClass() == obj.getClass()) {
			compositeProduct = (CompositeProduct) obj;
			this.menu.remove(compositeProduct);
		}
	}

	public void edit(Object newObj, Object oldObj) {
		assert isWellFormed();
		BaseProduct oldbaseProduct = new BaseProduct(null, 0);
		CompositeProduct oldcompositeProduct = new CompositeProduct(null, null);
		BaseProduct newbaseProduct = new BaseProduct(null, 0);
		CompositeProduct newcompositeProduct = new CompositeProduct(null, null);
		if ((oldbaseProduct.getClass() == oldObj.getClass()) && (newbaseProduct.getClass() == newObj.getClass())) {
			newbaseProduct = (BaseProduct) newObj;
			oldbaseProduct = (BaseProduct) oldObj;
			this.menu.remove(oldbaseProduct);
			this.menu.add(newbaseProduct);
		} else if ((oldcompositeProduct.getClass() == oldObj.getClass()) && (newcompositeProduct.getClass() == newObj.getClass())) {
			oldcompositeProduct = (CompositeProduct) oldObj;
			newcompositeProduct = (CompositeProduct) newObj;
			this.menu.remove(oldcompositeProduct);
			this.menu.add(newcompositeProduct);
		}
	}

	public float computePrice(Object obj) {
		assert isWellFormed();
		Order order = (Order) obj;
		assert obj != null;
		float total = 0;
		for(MenuItem menuItem : orders.get(order)) {
			total += menuItem.computePrice();
		}
		assert total > 0;
		return total;
	}

	public void generateBill(Object obj) {
		assert isWellFormed();
		assert obj != null;
		Order order = (Order) obj;
		FileWriter fw = new FileWriter(order.getOrderID());
		fw.writeInFile(order, orders.get(order), computePrice(order));
	}
	 
	 public synchronized void addObserver(Observer observer) {
		 super.addObserver(observer);
	 }
	 
	 public void computeOrder(Order order) {
		 this.setChanged();
		 notifyObservers(order);
	 }
	 
	 public void placeMenuItemInOrder(Order order, MenuItem menuItem) {
		 assert (menuItem != null);
		 if (!orders.containsKey(order)) {
			 orders.put(order, new ArrayList<MenuItem>());
		 }
		 orders.get(order).add(menuItem);
	 }

	public ArrayList<MenuItem> getMenu() {
		return menu;
	}

	public HashMap<Order, ArrayList<MenuItem>> getOrders() {
		return orders;
	}
	
	 public ArrayList<Order> getOrdersOnly(){
	        return new ArrayList<>(orders.keySet());
	    }
	
	public MenuItem findMenuItemByName(String product) {
		assert isWellFormed();
		for (MenuItem menuItem : menu) {
			if (menuItem.getMenuItemName().equals(product))
				return menuItem;
		}
		return null;
	}
	
	public Order findOrderByOrderID(int orderID) {
		assert isWellFormed();
		ArrayList<Order> orderList = getOrdersOnly();
		for (Order o : orderList) {
			if (o.getOrderID() == orderID)
				return o;
		}
		return null;
	}
	
	public boolean isWellFormed() {
		if (orders == null || menu == null)
			return false;
		return true;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

}
