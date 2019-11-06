import java.util.ArrayList;

import database.xml.Database;
import models.Position;

public class Main {
	public static void main(String[] args) {
		Database database= new Database();
		//String WOW = database.testQuery();
		//System.out.println(WOW);
		ArrayList<Position> positions = database.fetchStoredProducts(null);
		System.out.println("Objects in Array: "+positions.size());
		for (int i=0;i<positions.size();i++) {
			System.out.println("Position ("+i+"):");
			System.out.println("\tproduct_id:"+positions.get(0).getProductID());
			System.out.println("\tposition:"+positions.get(0).getID());
			System.out.println("\tshelf_section:"+positions.get(0).getShelfSection());
			System.out.println("\tshelf_place:"+positions.get(0).getShelfPlace());
		}
		
		
		
	}
}
