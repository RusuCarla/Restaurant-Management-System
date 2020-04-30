package business;

public class BaseProduct extends MenuItem{

	private String product;
	private float pricePerUnit;
	
	public BaseProduct(String product, float pricePerUnit) {
		this.product = product;
		this.pricePerUnit = pricePerUnit;
	}
	
	@Override
	public float computePrice() {
		return pricePerUnit;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}


	public float getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	@Override
	public String toString() {
		return "product=" + product + ", price=" + pricePerUnit;
	}

	@Override
	public String getMenuItemName() {
		return this.getProduct();
	}

	@Override
	public String listIngredients() {
		return this.getProduct();
	}
	

}
