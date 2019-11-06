package logic.interfaces;

public interface ProductInterface {
	int getID();
	int getNmb();
	String getName();
	CategoryInterface getCategory();
	int getPrize();
	int getWeight();
	public boolean renameProduct();
}
