package business;

public interface IRestaurantProcessing {
	
	/**
	 * @pre obj!= null and isWellFormed()
	 * @post menu.size()=menu.size()@pre +1 || orders.size()=menu.size()@pre+1;
	 * @param obj
	 * @return
	 */
	public Object create(Object obj);
	/**
	 * @pre obj!= null and isWellFormed()
	 * @post menu.size()=menu.size()@pre -1 || orders.size()=menu.size()@pre-1;
	 * @param obj
	 * @return
	 */
	public void delete(Object obj);
	/**
	 * @pre obj!= null and isWellFormed()
	 * @post menu.size()=menu.size()@pre || orders.size()=menu.size()@pre;
	 * @param obj
	 * @return
	 */
	public void edit(Object newObj, Object oldObj);
	/**
	 * @pre obj!= null and isWellFormed()
	 * @post @return >0
	 * @param obj
	 * @return
	 */
	public float computePrice(Object obj);
	/**
	 * @pre obj!= null and isWellFormed()
	 * @param obj
	 * @return
	 */
	public void generateBill(Object obj);
}
