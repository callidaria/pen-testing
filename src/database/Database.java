package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.Position;
import src.interfaces.PositionInterface;
import src.interfaces.ProductInterface;

public class Database implements DatabaseInterface {
	
	String ROWID = "ROWID";
	
	String DBTYP = "sqlite";
	
	String DBPATH = "D:\\\\Projects\\\\Java Projects\\\\pen-testing\\\\sqlite\\\\pen_testing.db";
	
	String DBUSER = "root";
	
	String DBPASSWORD = "password";

	Connection conn;
	@Override
	public boolean connect() {
		try {
			Connection conn = DriverManager.getConnection(
		               "jdbc:"+this.DBTYP+":"+this.DBPATH,
		               this.DBUSER, this.DBPASSWORD);
				this.conn=conn;
		         
		      } catch(SQLException ex) {
		         ex.printStackTrace();
	      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultSet query(String query) {
		// TODO Auto-generated method stub
		ResultSet rset = null;
		try(Statement stmt = this.conn.createStatement()){
			
	        String strSelect = query;
	        System.out.println("Query: " + strSelect + "\n"); // Echo For debugging

	        rset = stmt.executeQuery(strSelect);
			}
			catch(SQLException ex){
				ex.printStackTrace();
			}
        return rset;
	}

	@Override
	public ArrayList<Position> searchProduct() {
		if(true) {
			ResultSet result = this.query("select * from products");
		}
		
		return null;
	}
	
	public ArrayList<String> fetchStoredProducts(){
		String title="Keine";
		String strSelect = "select products.name as productName, inventory.position as inventoryPosition from products inner join inventory on products.product_id=inventory.product_id;";
		try(Statement stmt = this.conn.createStatement()){
	        ResultSet result = stmt.executeQuery(strSelect);
	        System.out.println("The records selected are:");

	        while(result.next()) {   // Move the cursor to the next row, return false if no more row
	           String productName = result.getString("productName");
	           String productPosition = result.getString("inventoryPosition");
	           System.out.println("Name:"+productName+", Position: "+productPosition);
	        }
	        System.out.println(":end");
			}
			catch(SQLException ex){
				ex.printStackTrace();
			}
			return null;
	}
	
	public ArrayList<Position> ResultSetToPosition(ResultSet result) {
		ArrayList<Position> positions;
		while(result.next() || result.isFirst()) {
				Position position = new Position();
				position.setId = result.getInt("id");
				position.setPosition = result.getInt("position");
				position.setProductID = result.getInt("product_id");
				positions.add(position);
	        }
		return positions;
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
	
	public Database() {
		this.connect();
	}

}
