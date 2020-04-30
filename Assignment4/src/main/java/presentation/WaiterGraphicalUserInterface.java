package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Order;
import business.Restaurant;
import data.RestaurantSerializator;

public class WaiterGraphicalUserInterface extends JFrame{
	
	private int WIDTH=1000, HEIGHT=700;
	
	JPanel panel = new JPanel();
	JPanel tablePanel = new JPanel();
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JPanel fieldPanel = new JPanel();
	JPanel fieldPanel2 = new JPanel();
	JPanel buttonPanel = new JPanel();
	
	JTextField orderIDTF = new JTextField();
	JTextField dateTF = new JTextField();
	JTextField menuItemIngredientsTF = new JTextField();
	
	JLabel orderIDLabel = new JLabel("OrderID");
	JLabel dateLabel = new JLabel("Date");
	JLabel menuItemIngredientsLabel = new JLabel("Products");
	
	private Restaurant restaurant;
	private RestaurantSerializator serializator = new RestaurantSerializator();
	
	public WaiterGraphicalUserInterface() {
restaurant = serializator.deserialize();
		
		this.setTitle("Waiter");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		tablePanel.setLayout(new BorderLayout());
			
		JButton addItemButton = new JButton("Create Order");
		addItemButton.addActionListener(new OnClick("add"));
		JButton generateBillButton = new JButton("Generate bill");
		generateBillButton.addActionListener(new OnClick("generate"));
		JButton viewMenuButton = new JButton("View");
		viewMenuButton.addActionListener(new OnClick("view"));
		
		orderIDTF.setPreferredSize(new Dimension(100, 20));
		menuItemIngredientsTF.setPreferredSize(new Dimension(100, 20));
		dateTF.setPreferredSize(new Dimension(100, 20));
		
		fieldPanel.add(orderIDLabel);
		fieldPanel.add(orderIDTF);
		fieldPanel.add(dateLabel);
		fieldPanel.add(dateTF);
		fieldPanel2.add(menuItemIngredientsLabel);
		fieldPanel2.add(menuItemIngredientsTF);

		buttonPanel.add(addItemButton);
		buttonPanel.add(generateBillButton);
		buttonPanel.add(viewMenuButton);
		
		leftPanel.add(fieldPanel);
		leftPanel.add(fieldPanel2);
		leftPanel.add(buttonPanel);
		rightPanel.add(tablePanel);
		panel.add(leftPanel);
		panel.add(rightPanel);
		
		this.setContentPane(panel);
		this.setVisible(true);
	}
	
private class OnClick implements ActionListener {
		
		String button;
		
		public OnClick(String button) {
			this.button = button;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			switch (button) {
				case "add":
					if (dateTF.getText().matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")){
						Order order = (Order) restaurant.create(dateTF.getText());
						String ing = menuItemIngredientsTF.getText();
						ing.replaceAll("\\s", "");
						String[] ings = ing.split(",");
						for(int i=0; i<ings.length; i++) {
							MenuItem item = restaurant.findMenuItemByName(ings[i]);
							restaurant.placeMenuItemInOrder(order, item);
						}
						restaurant.computeOrder(order);
						serializator.serialize(restaurant);
					} else {
						JOptionPane.showMessageDialog(null, "date format should be dd/mm/yyyy", "Error", JOptionPane.ERROR_MESSAGE);							
					}
					break;
				case "generate":
					if (orderIDTF.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "order id must be given", "Error", JOptionPane.ERROR_MESSAGE);							
					}else {
						restaurant.generateBill(restaurant.findOrderByOrderID(Integer.parseInt(orderIDTF.getText())));
						JOptionPane.showMessageDialog(null, "Bill generated", "Notice",JOptionPane.INFORMATION_MESSAGE);
					}
					break;
				case "view":
					tablePanel.removeAll();
					JTable table = createTable();
					tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tablePanel.add(table, BorderLayout.CENTER);
					WaiterGraphicalUserInterface.this.revalidate();
					break;
			}
		}
	}

	
	private JTable createTable() {
		JTable table;
		Object[][] entries = new Object[restaurant.getOrdersOnly().size()][4];
		String[] collumns = {"OrderID", "TableID", "Date", "Price"};

		for(int i = 0; i < restaurant.getOrders().size(); i++) {
			entries[i][0] = restaurant.getOrdersOnly().get(i).getOrderID();
			entries[i][1] = restaurant.getOrdersOnly().get(i).getTableID();
			entries[i][2] = restaurant.getOrdersOnly().get(i).getDate();
			entries[i][3] = restaurant.computePrice(restaurant.getOrdersOnly().get(i));
		}
		table = new JTable(entries, collumns);
		return table;
	}
	
	public static void main(String[] args) {
		WaiterGraphicalUserInterface waiterGraphicalUserInterface = new WaiterGraphicalUserInterface();
		ChefGraphicalUserInterface chefGUI = new ChefGraphicalUserInterface();
		waiterGraphicalUserInterface.restaurant.addObserver(chefGUI);
	}
	
}
