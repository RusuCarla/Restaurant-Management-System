package business;

public abstract class MenuItem implements java.io.Serializable{
	
	public abstract float computePrice();
	public abstract String getMenuItemName();
	public abstract String listIngredients();
}

