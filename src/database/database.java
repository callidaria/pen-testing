package database;

import java.sql.*;

import app.interfaceProduct;

public interface database {
	public boolean connect();
	public ResultSet query();
	public interfaceProduct[] searchProduct();
}