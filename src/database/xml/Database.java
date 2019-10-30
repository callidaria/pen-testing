package database.xml;

import java.sql.Connection;
import java.util.ArrayList;

import database.DatabaseInterface;
import logic.interfaces.StoredProductInterface;

public class Database implements DatabaseInterface {

	@Override
	public Connection connect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<StoredProductInterface> retrieveStoredProducts(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<StoredProductInterface> fetchStoredProducts(Object[] options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<StoredProductInterface> searchStoredProducts(String productName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addStoredProduct(StoredProductInterface storedProduct) {
		// TODO Auto-generated method stub
		return false;
	}

}
