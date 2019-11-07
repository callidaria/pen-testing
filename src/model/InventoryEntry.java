package model;

import model.interfaces.PositionInterface;

public class InventoryEntry implements PositionInterface{
	private int shelfSection;
	private int shelfPlace;
	private int productID;
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

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}
	
	public InventoryEntry(int shelfSection, int shelfPlace, int productID) {
		this.shelfSection=shelfSection;
		this.shelfPlace=shelfPlace;
		this.productID=productID;
	}
	
	public InventoryEntry(int shelfSection, int shelfPlace, int productID, Product product) {
		this.shelfSection=shelfSection;
		this.shelfPlace=shelfPlace;
		this.productID=productID;
		this.product=product;
	}
	
	
}
