package database;

import java.sql.*;
import java.util.ArrayList;


import src.interfaces.StoredProductInterface;

public interface DatabaseInterface {
	public Connection connect();
	public ArrayList<StoredProductInterface>fetchStoredProducts(Object[] options);
	public ArrayList<StoredProductInterface>searchStoredProducts(String productName);
	public boolean addStoredProduct(StoredProductInterface storedProduct);
}