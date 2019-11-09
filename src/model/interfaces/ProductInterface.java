package model.interfaces;

public interface ProductInterface {
	int getID();
	String getName();
	CategoryInterface getCategory();
	int getPrize();
	int getWeight();
	public boolean renameProduct();
}
