package business;

import java.util.Objects;

public class Order implements java.io.Serializable{
	private int orderID;
	private int tableID;
	private String date;
	
	public int getTableID() {
		return tableID;
	}

	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderID,date,tableID);
	}
	
	 @Override
	 public boolean equals(Object obj) {
	    if (this.getClass() == obj.getClass()) {
		    Order order = (Order) obj;
		    if (this.orderID == order.orderID && this.date.equals(order.date) && this.tableID ==order.tableID)
		    	return true;
		    else 
		    	return false;
	    } else
	    	return false;
	 }

	@Override
	public String toString() {
		return "orderID=" + orderID + ", tableID=" + tableID + ", date=" + date;
	}
	
}
