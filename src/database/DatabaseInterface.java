package database;

import java.sql.*;

import app.ProductInterface;

public interface DatabaseInterface {
	public boolean connect();
	public ResultSet query();
	public ProductInterface[] searchProduct();
}