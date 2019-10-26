package database;

import java.sql.*;

import app.InterfaceProduct;

public interface database {
	public boolean connect();
	public ResultSet query();
	public InterfaceProduct[] searchProduct();
}