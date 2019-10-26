package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.ProductInterface;

public class Database implements DatabaseInterface {

	Connection conn;
	@Override
	public boolean connect() {
		try {
			Connection conn = DriverManager.getConnection(
		               "jdbc:mysql://localhost:3306/pen_testing?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
		               "root", "password");
				this.conn=conn;
		         
		      } catch(SQLException ex) {
		         ex.printStackTrace();
	      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultSet query() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductInterface[] searchProduct() {
		return null;
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
