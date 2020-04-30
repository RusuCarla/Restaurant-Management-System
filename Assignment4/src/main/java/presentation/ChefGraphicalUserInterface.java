package presentation;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import business.Order;
import business.Restaurant;

public class ChefGraphicalUserInterface extends JFrame implements Observer{
	
	private JPanel panel;
	private int WIDTH=400, HEIGHT=400;
	
	private Restaurant restaurant;
	
	public ChefGraphicalUserInterface() {
		this.setTitle("Chef");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		panel = new JPanel();
		this.setContentPane(panel);
		this.setVisible(true);
	}

	public void update(Observable arg0, Object arg1) {
		Order order = (Order) arg1;
		System.out.println("Chef received: " + order.toString());
		JOptionPane.showMessageDialog(null, "Chef received: "+order.toString(), "Notice",JOptionPane.INFORMATION_MESSAGE);
		this.revalidate();
	}

}
