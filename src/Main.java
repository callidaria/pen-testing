import java.util.ArrayList;

import database.xml.Database;
import model.InventoryEntry;
import model.Product;

public class Main {
	public static void main(String[] args) {
		Database database= new Database();
		//String WOW = database.testQuery();
		//System.out.println(WOW);
		ArrayList<InventoryEntry> inventoryEntry = database.retrieveInventoryEntry();
		System.out.println("Objects in Array: "+inventoryEntry.size());
		for (int i=0;i<inventoryEntry.size();i++) {
			System.out.println("InventoryEntry ("+i+"):");
			System.out.println("\tproduct_id:"+inventoryEntry.get(i).getProductID());
			System.out.println("\t\tproduct_name:"+inventoryEntry.get(i).product.getName());
			System.out.println("\t\tproduct_count:"+inventoryEntry.get(i).product.getCount());
			System.out.println("\tposition:"+inventoryEntry.get(i).getID());
			System.out.println("\tshelf_section:"+inventoryEntry.get(i).getShelfSection());
			System.out.println("\tshelf_place:"+inventoryEntry.get(i).getShelfPlace());
		}
		
		ArrayList<Product> products = database.retrieveProducts();
		
		for (int i=0;i<products.size();i++) {
			System.out.println("Product ("+i+"):");
			System.out.println("\tid:"+products.get(i).getId());
			System.out.println("\tname:"+products.get(i).getName());
			System.out.println("\tcount:"+products.get(i).getCount());
		}
		
		
	}
}
