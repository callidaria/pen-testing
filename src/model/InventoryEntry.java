package model;

import model.interfaces.PositionInterface;

public class InventoryEntry implements PositionInterface{
	/*to-do:
	 * decide on UID (UniqueIDentifier);
	 * give shelfSection & shelfPlace better names.
	 * give getID better name.
	 *
	 */
	private int shelfSection;
	private int shelfPlace;
	public Product product;
	
	public int getID() {
		// TODO Auto-generated method stub
		String sid = ""+shelfSection+shelfPlace;
		int iid = Integer.parseInt(sid);
		return iid;
	}
	
	public int getShelfSection() {
		return shelfSection;
	}
	public void setShelfSection(int shelfSection) {
		this.shelfSection = shelfSection;
	}
	
	
	public int getShelfPlace() {
		return shelfPlace;
	}
	public void setShelfPlace(int shelfPlace) {
		this.shelfPlace = shelfPlace;
	}
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public int getUID() {
		return this.getID();
	}
	
	public InventoryEntry(int shelfSection, int shelfPlace, Product product) {
		this.shelfSection=shelfSection;
		this.shelfPlace=shelfPlace;
		this.product=product;
	}
	
	@Deprecated
	public InventoryEntry(int shelfSection, int shelfPlace, int UID) {
		this.shelfSection=shelfSection;
		this.shelfPlace=shelfPlace;

	}
	
	@Deprecated
	public InventoryEntry(int shelfSection, int shelfPlace, int UID, Product product) {
		this.shelfSection=shelfSection;
		this.shelfPlace=shelfPlace;
		this.product=product;
	}
	
	public boolean validate() {
		return true;
	}
}
