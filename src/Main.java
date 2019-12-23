import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.xml.transform.TransformerException;

import database.xml.Database;
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
			System.out.println("Objects in Array: "+inventoryEntry.size());
			for (int i=0;i<inventoryEntry.size();i++) {
				System.out.println(inventoryEntry.get(i));
				/*System.out.println("InventoryEntry ("+i+"):");
				System.out.println("\tUID :"+inventoryEntry.get(i).getUID());
				System.out.println("\t\tproduct_name:"+inventoryEntry.get(i).product.getName());
				System.out.println("\t\tproduct_count:"+inventoryEntry.get(i).product.getCount());
				System.out.println("\t\tproduct_weight:"+inventoryEntry.get(i).product.getWeight()+"g");
				System.out.println("\t\tproduct_prize:"+inventoryEntry.get(i).product.getPrize()+"c");
				System.out.println("\tposition:"+inventoryEntry.get(i).getID());
				System.out.println("\tshelf_section:"+inventoryEntry.get(i).getShelfSection());
				System.out.println("\tshelf_place:"+inventoryEntry.get(i).getShelfPlace());*/
			}
			
			Product pro = new Product("Simple Pen 2nd", 12, 12, 99);
			InventoryEntry ie = new InventoryEntry(100,200,pro);
			try {
				Database.replaceInventoryEntry(100100, ie);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Die gewählte UID ist belegt.");
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
