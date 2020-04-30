package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import business.MenuItem;
import business.Order;
import business.Restaurant;

public class RestaurantSerializator {
	
	public void serialize(Restaurant restaurant) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("restaurant.ser");
			ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
			outputStream.writeObject(restaurant);
			fileOutputStream.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Restaurant deserialize(){
		Restaurant restaurant = null;
		try {
			FileInputStream fileInputStream = new FileInputStream("restaurant.ser");
			ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
			restaurant = (Restaurant) inputStream.readObject();
			fileInputStream.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return restaurant;
	}
}
