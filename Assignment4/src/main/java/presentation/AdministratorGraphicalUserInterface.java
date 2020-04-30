package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Restaurant;
import data.RestaurantSerializator;

public class AdministratorGraphicalUserInterface extends JFrame{
	
	private int WIDTH=1000, HEIGHT=700;
	
	JPanel panel = new JPanel();
	JPanel tablePanel = new JPanel();
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JPanel fieldPanel = new JPanel();
	JPanel fieldPanel2 = new JPanel();
	JPanel buttonPanel = new JPanel();
	
	JTextField menuItemNameTF = new JTextField();
	JTextField menuItemEditNameTF = new JTextField();
	JTextField menuItemPriceTF = new JTextField();
	JTextField menuItemIngredientsTF = new JTextField();
	
	JLabel menuItemNameLabel = new JLabel("Product name");
	JLabel menuItemEditNameLabel = new JLabel("New name");
	JLabel menuItemPriceLabel = new JLabel("Price");
	JLabel menuItemIngredientsLabel = new JLabel("Ingredients");
	
	private Restaurant restaurant;
	private RestaurantSerializator serializator = new RestaurantSerializator();
	
	public AdministratorGraphicalUserInterface() {
		restaurant = serializator.deserialize();
		
		this.setTitle("Administrator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		tablePanel.setLayout(new BorderLayout());
			
		JButton addItemButton = new JButton("Add");
		addItemButton.addActionListener(new OnClick("add"));
		JButton editItemButton = new JButton("Edit");
		editItemButton.addActionListener(new OnClick("edit"));
		JButton deleteItemButton = new JButton("Delete");
		deleteItemButton.addActionListener(new OnClick("delete"));
		JButton viewMenuButton = new JButton("View");
		viewMenuButton.addActionListener(new OnClick("view"));
		
		menuItemNameTF.setPreferredSize(new Dimension(100, 20));
		menuItemIngredientsTF.setPreferredSize(new Dimension(100, 20));
		menuItemPriceTF.setPreferredSize(new Dimension(100, 20));
		menuItemEditNameTF.setPreferredSize(new Dimension(100, 20));
		
		fieldPanel.add(menuItemNameLabel);
		fieldPanel.add(menuItemNameTF);
		fieldPanel.add(menuItemIngredientsLabel);
		fieldPanel.add(menuItemIngredientsTF);
		fieldPanel2.add(menuItemPriceLabel);
		fieldPanel2.add(menuItemPriceTF);
		fieldPanel2.add(menuItemEditNameLabel);
		fieldPanel2.add(menuItemEditNameTF);
		
		buttonPanel.add(addItemButton);
		buttonPanel.add(editItemButton);
		buttonPanel.add(deleteItemButton);
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
					if (menuItemIngredientsTF.getText().equals("")) {
						BaseProduct b1 = new BaseProduct(menuItemNameTF.getText(), Float.parseFloat(menuItemPriceTF.getText()));
						restaurant.create(b1);
					} else {
						ArrayList<MenuItem> bases = new ArrayList<MenuItem>();
						String ing = menuItemIngredientsTF.getText();
						//ing.replaceAll("\\s", "");
						String[] ings = ing.split(",");
						for(int i=0; i<ings.length; i++) {
							System.out.println(ings[i]);
							MenuItem b1 = restaurant.findMenuItemByName(ings[i]);
							bases.add(b1);
						}
						CompositeProduct c1 = new CompositeProduct(menuItemNameTF.getText(), bases);
						c1.listIngredients();
						restaurant.create(c1);
					}
					serializator.serialize(restaurant);
					break;
				case "edit":
					if (menuItemIngredientsTF.getText().equals("")) {
						BaseProduct b1 = new BaseProduct(menuItemEditNameTF.getText(), Float.parseFloat(menuItemPriceTF.getText()));
						restaurant.edit(b1, restaurant.findMenuItemByName(menuItemNameTF.getText()));
					} else {
						ArrayList<MenuItem> bases = new ArrayList<MenuItem>();
						String ing = menuItemIngredientsTF.getText();
						ing.replaceAll("\\s", "");
						String[] ings = ing.split(",");
						for(int i=0; i<ings.length; i++) {
							BaseProduct b1 = new BaseProduct(ings[i], restaurant.findMenuItemByName(ings[i]).computePrice());
							bases.add(b1);
						}
						CompositeProduct c1 = new CompositeProduct(menuItemNameTF.getText(), bases);
						restaurant.edit(c1, restaurant.findMenuItemByName(menuItemNameTF.getText()));
					}
					serializator.serialize(restaurant);
					break;
				case "delete":
					restaurant.delete(restaurant.findMenuItemByName(menuItemNameTF.getText()));
					serializator.serialize(restaurant);
					break;
				case "view":
					tablePanel.removeAll();
					JTable table = createTable();
					tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tablePanel.add(table, BorderLayout.CENTER);
					AdministratorGraphicalUserInterface.this.revalidate();
					break;
			}
		}
	}

	
	private JTable createTable() {
		JTable table;
		Object[][] entries = new Object[restaurant.getMenu().size()][3];
		String[] collumns = {"Product", "Ingredients", "Price"};

		for(int i = 0; i < restaurant.getMenu().size(); i++) {
			entries[i][0] = restaurant.getMenu().get(i).getMenuItemName();
			if(restaurant.getMenu().get(i).getClass().getName().endsWith("CompositeProduct"))
				entries[i][1] = ((CompositeProduct) restaurant.getMenu().get(i)).listIngredients();
			else
				entries[i][1] = "";
			entries[i][2] = restaurant.getMenu().get(i).computePrice();
		}
		table = new JTable(entries, collumns);
		return table;
	}
	
	public static void main(String[] args) {
		AdministratorGraphicalUserInterface administratorGraphicalUserInterface = new AdministratorGraphicalUserInterface();
	}
	
}
