import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.xml.transform.TransformerException;

import database.xml.Database;
import model.Category;
import model.InventoryEntry;
import model.Product;
import view.TextAreaLogProgram;

public class Main {
	public static void main(String[] args) {
		
		/* Beispiel ist eine Unterklasse von Main
		 * wenn das jemand besser strukturieren m�chte, gerne!
		 */
		Main main = new Main();
		Main.DatabaseExamples dbEx = main.new DatabaseExamples();
	}
	
	
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
			for (int i=0;i<inventoryEntry.size();i++) {
				System.out.println(inventoryEntry.get(i));
			}
			
			Product pro = new Product("Pen 2", 12, 12, 99);
			InventoryEntry ie = new InventoryEntry(100,100,pro);
			System.out.println("Free Space: "+Database.freeSpace(100));
			
			try {
				System.out.println("Database status: "+Database.validate());
				Database.replaceInventoryEntry(100100,ie);
				Database.editAttributeOfInventoryEntry(100100, "count", "1");
				Database.deleteInventoryEntry(100100, false);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Error: "+e.getMessage());
			}

			
			ArrayList<Category> categories = Database.retrieveCategories();
			for (int i=0;i<categories.size();i++) {
				System.out.println(categories.get(i));
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
