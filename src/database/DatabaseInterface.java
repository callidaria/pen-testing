package database;

import java.sql.*;
import java.util.ArrayList;

import app.Position;
import app.PositionInterface;
import app.ProductInterface;

public interface DatabaseInterface {
	public boolean connect();
	public ResultSet query(String query);
	public ArrayList<Position> searchProduct();
}