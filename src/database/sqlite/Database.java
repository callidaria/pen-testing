package database.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DatabaseInterface;
import logic.interfaces.StoredProductInterface;

public class Database implements DatabaseInterface{
	
	static final String ROWID = "ROWID";
	
	static final String DBTYP = "sqlite";
	
	static final String DBPATH = "sqlite\\\\pen_testing.db";
	
	static final String DBUSER = "root";
	
	static final String DBPASSWORD = "password";
	
	final String DBCON = "jdbc:"+Database.DBTYP+":"+Database.DBPATH;

	Connection conn;

	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(this.DBCON);
		  } catch(SQLException ex) {
		         ex.printStackTrace();
	      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
		// TODO Auto-generated method stub
		return conn;
	}
	
	//StoredProducts start:
	public ArrayList<StoredProductInterface>retrieveStoredProducts(String query){
		System.out.println("Query:"+query);
		try(Connection conn = connect();Statement stmt = conn.createStatement();){
	        ResultSet result = stmt.executeQuery(query);
	        System.out.println("The records selected are:");

	        while(result.next()) {   // Move the cursor to the next row, return false if no more row
	           String productName = result.getString("productName");
	           String productPosition = result.getString("inventoryPosition");
	           System.out.println("Name:"+productName+", Position: "+productPosition);
	        }
	        System.out.println(":end");
	        conn.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		return null;		
	}

	public ArrayList<StoredProductInterface> fetchStoredProducts(Object[] options){
		String query = "select products.name as productName, inventory.position as inventoryPosition from products inner join inventory on products.product_id=inventory.product_id;";
		this.retrieveStoredProducts(query);
		return null;		
	}
	
	public ArrayList<StoredProductInterface> searchStoredProducts(String productName) {
		String query = "select products.name as productName, inventory.position as inventoryPosition from products inner join inventory on products.product_id=inventory.product_id where products.name='"+productName+"';";
		this.retrieveStoredProducts(query);
		return null;
	}



	public boolean addStoredProduct(StoredProductInterface storedProduct) {
		// TODO Auto-generated method stub
		return false;
	}
	

	
	public String testQuery() {
		String title="Keine";
		try(Statement stmt = this.conn.createStatement()){
			// TODO Auto-generated method stub
			// Step 3: Execute a SQL SELECT query. The query result is returned in a 'ResultSet' object.
	        String strSelect = "select name from products";
	        System.out.println("The SQL statement is: " + strSelect + "\n"); // Echo For debugging

	        ResultSet rset = stmt.executeQuery(strSelect);

	        // Step 4: Process the ResultSet by scrolling the cursor forward via next().
	        //  For each row, retrieve the contents of the cells with getXxx(columnName).
	        System.out.println("The records selected are:");
	        while(rset.next()) {   // Move the cursor to the next row, return false if no more row
	           title = rset.getString("name");
	        }
	        System.out.println("testQuery return:"+title);
			}
			catch(SQLException ex){
				ex.printStackTrace();
			}
        return title;
		
	}
	


}
