package database.inhouse;

import java.sql.Connection;
import java.util.ArrayList;

import database.DatabaseInterface;
import models.StoredProduct;

public class Database implements DatabaseInterface {

	@Override
	public Connection connect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<StoredProduct> retrieveStoredProducts(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<StoredProduct> fetchStoredProducts(Object[] options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<StoredProduct> searchStoredProducts(String productName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addStoredProduct(StoredProduct storedProduct) {
		// TODO Auto-generated method stub
		return false;
	}

}
