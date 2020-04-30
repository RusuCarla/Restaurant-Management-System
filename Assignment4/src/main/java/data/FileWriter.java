package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import business.MenuItem;
import business.Order;

public class FileWriter {
	
	private String filename;
	private PrintWriter writer;
	
	public FileWriter(int orderNo) {

		filename = "order_no_" + orderNo + ".txt";
		writer = null;
		try {
			writer = new PrintWriter(filename, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void writeInFile(Order order, ArrayList<MenuItem> menu, float total) {
			
			writer.println("order_information: " + order.toString() + "\n");
			writer.println("list_of_products:\n");
			
			for(MenuItem menuItem : menu) {
				writer.println(menuItem.toString() + "\n");	            				
			}
			writer.println("total_amount_to_pay: " + total);
			writer.close();
	}
}
