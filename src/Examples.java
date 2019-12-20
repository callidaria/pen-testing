import java.util.ArrayList;

import javax.swing.SwingUtilities;

import database.xml.Database;
import model.InventoryEntry;
import model.Product;
import view.TextAreaLogProgram;

public class Examples {

	public class DatabaseExamples{
		public DatabaseExamples() {
			/* retrieveInventoryEntries liefert euch alle InventoryEntries in einer List.
			 * retrieveProduct liefert euch nur die Produkte.
			 * 
			 * beide Funktionen sind statisch und ihr braucht kein Objekt davon erstellen.
			 * 
			 * die Klassen Product und InventoryEntries sind in "UML Klassendiagram_Model" beschrieben
			 * 
			 * die Reinfolge der ArrayList ist NICHT gleich der ID
			 * 
			 * 
			 * API zur ArrayList: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
			 */
			
			
			
			ArrayList<InventoryEntry> inventoryEntry = Database.retrieveInventoryEntries();
			System.out.println("Objects in Array: "+inventoryEntry.size());
			for (int i=0;i<inventoryEntry.size();i++) {
				System.out.println("InventoryEntry ("+i+"):");
				System.out.println("\tproduct_id:"+inventoryEntry.get(i).getUID());
				System.out.println("\t\tproduct_name:"+inventoryEntry.get(i).product.getName());
				System.out.println("\t\tproduct_count:"+inventoryEntry.get(i).product.getCount());
				System.out.println("\tposition:"+inventoryEntry.get(i).getID());
				System.out.println("\tshelf_section:"+inventoryEntry.get(i).getShelfSection());
				System.out.println("\tshelf_place:"+inventoryEntry.get(i).getShelfPlace());
			}
			
			ArrayList<Product> products = Database.retrieveProducts();
			
			for (int i=0;i<products.size();i++) {
				System.out.println("Product ("+i+"):");
				System.out.println("\tid:"+products.get(i).getId());
				System.out.println("\tname:"+products.get(i).getName());
				System.out.println("\tcount:"+products.get(i).getCount());
			}
			
			
			SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new TextAreaLogProgram().setVisible(true);
	            }
	        });
			
		}
	}
}
