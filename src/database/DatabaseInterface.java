package database;

import java.sql.*;
import java.util.ArrayList;

import src.Position;
import src.interfaces.PositionInterface;
import src.interfaces.ProductInterface;

public interface DatabaseInterface {
	public boolean connect();
	public ResultSet query(String query);
	public ArrayList<Position> searchProduct();
}