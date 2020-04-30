package business;

import java.awt.Menu;
import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem{

	private List<MenuItem> baseProductList = new ArrayList<MenuItem>();
	private String product;
	
	public CompositeProduct(String product, List<MenuItem> menuItem) {
		this.product = product;
		this.baseProductList = menuItem;
	}
	
	@Override
	public float computePrice() {
		BaseProduct baseProduct = new BaseProduct(null, 0);
		CompositeProduct compositeProduct = new CompositeProduct(null, null);
		float total=0;
		for (MenuItem p : baseProductList) {
			total += p.computePrice();
		}
		assert (total>=0);
		return total;
	}
	
	@Override
	public String listIngredients() {
		String list="";
		System.out.println(baseProductList.size());
		for(MenuItem p : baseProductList) {
			list+=p.getMenuItemName()+", ";
		}
		list = list.substring(0,list.length()-2);
		return list;
	}
	
	public void addBaseProduct(MenuItem p) {
		this.baseProductList.add(p);
	}
	
	public void deleteBaseProduct(MenuItem p) {
		this.baseProductList.remove(p);
	}

	public List<MenuItem> getBaseProductList() {
		return baseProductList;
	}

	public void setBaseProductList(List<MenuItem> baseProductList) {
		this.baseProductList = baseProductList;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "product=" + product + ", ingredients=" + listIngredients() + ", price=" + computePrice();
	}

	@Override
	public String getMenuItemName() {
		return this.getProduct();
	}

}
