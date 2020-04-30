package start;

import java.util.ArrayList;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Order;
import business.Restaurant;
import data.FileWriter;
import data.RestaurantSerializator;
import presentation.AdministratorGraphicalUserInterface;
import presentation.ChefGraphicalUserInterface;
import presentation.WaiterGraphicalUserInterface;

public class Start {
	public static void main (String[] args) {
		BaseProduct b1 = new BaseProduct("Rosii cherry", 3.5f);
		BaseProduct b2 = new BaseProduct("Mozarella", 5f);
		BaseProduct b3 = new BaseProduct("Busuioc", 4f);
		BaseProduct b4 = new BaseProduct("Otet balsamic", 4f);
		BaseProduct b5 = new BaseProduct("Oua", 6f);
		BaseProduct b6 = new BaseProduct("Ciuperci", 4.5f);
		BaseProduct b7 = new BaseProduct("Carne de vita", 25f);
		BaseProduct b8 = new BaseProduct("Cartofi prajiti", 7f);
		
		ArrayList<MenuItem> caprese = new ArrayList<MenuItem>();
		ArrayList<MenuItem> omleta = new ArrayList<MenuItem>();
		ArrayList<MenuItem> antricot = new ArrayList<MenuItem>();
		
		caprese.add(b1);
		caprese.add(b2);
		caprese.add(b3);
		caprese.add(b4);
		
		omleta.add(b5);
		omleta.add(b6);
		omleta.add(b2);
		
		antricot.add(b7);
		antricot.add(b8);
		antricot.add(b3);
		
		CompositeProduct c1 = new CompositeProduct("Salata caprese", caprese);
		CompositeProduct c2 = new CompositeProduct("Omleta", omleta);
		CompositeProduct c3 = new CompositeProduct("Antricot de vita", antricot);
		
		ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
		menu.add(c1);
		menu.add(c2);
		menu.add(c3);
		menu.add(b8);
		
		RestaurantSerializator serializator = new RestaurantSerializator();
		/*for (MenuItem menuItem: menu) {
			System.out.println(menuItem.getMenuItemName());
			System.out.println(menuItem.listIngredients());
			System.out.println(menuItem.computePrice());
			System.out.println();
		}*/
		
		Restaurant restaurant = new Restaurant();
		
		Order o1 = (Order)restaurant.create("03/06/2019");
		Order o2 = (Order)restaurant.create("01/06/2019");
		
		restaurant.create(c1);
		restaurant.create(c2);
		restaurant.create(c3);
		restaurant.create(b8);
		
		restaurant.placeMenuItemInOrder(o1, b8);
		restaurant.placeMenuItemInOrder(o1, c2);
		restaurant.placeMenuItemInOrder(o1, c3);
		restaurant.placeMenuItemInOrder(o2, c1);
		restaurant.placeMenuItemInOrder(o2, c2);
		
		serializator.serialize(restaurant);
		
		Restaurant restaurant2 = serializator.deserialize();
		ArrayList<MenuItem> menu2 = restaurant2.getMenu();
		for (MenuItem menuItem: menu2) {
			System.out.println(menuItem.getMenuItemName());
			System.out.println(menuItem.listIngredients());
			System.out.println(menuItem.computePrice());
			System.out.println();
		}
		
		restaurant2.generateBill(o1);
		restaurant2.generateBill(o2);
		
		ChefGraphicalUserInterface chefGraphicalUserInterface = new ChefGraphicalUserInterface();
		restaurant2.addObserver(chefGraphicalUserInterface);
		WaiterGraphicalUserInterface waiterGraphicalUserInterface = new WaiterGraphicalUserInterface();
		AdministratorGraphicalUserInterface administratorGraphicalUserInterface = new AdministratorGraphicalUserInterface();
	}
}
