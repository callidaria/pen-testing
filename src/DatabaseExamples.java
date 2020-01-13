import java.util.ArrayList;
import java.util.Date;

import database.Database;
import model.Category;
import model.CodedException;
import model.InventoryEntry;
import model.Product;
import view.TextAreaLogProgram;

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
		
		
		Date start;
		Date end;
		
		/*
		start = new Date();
		try {
			Database.addTestInventoryEntries(10, 999, false);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		end = new Date();
		System.out.println(end.getTime()-start.getTime());
		*/
		
		start = new Date();
		ArrayList<Category> categories = Database.retrieveCategories();
		end = new Date();
		System.out.println(end.getTime()-start.getTime());
		start = new Date();
		ArrayList<InventoryEntry> inventoryEntries = Database.retrieveInventoryEntriesWithCategory(categories);
		end = new Date();
		System.out.println(end.getTime()-start.getTime());
		
		Product pro = new Product("Pen 2", 12, 12, 99, 1);
		InventoryEntry ie = new InventoryEntry(100,100,pro);
		/*
		start = new Date();
		System.out.println("Free Space: "+Database.freeSpace(100));
		end = new Date();
		
		System.out.println(end.getTime()-start.getTime());
		*/
		try {
			/*
			start = new Date();
			System.out.println("Database status: "+Database.validate());
			end = new Date();
			System.out.println(end.getTime()-start.getTime());
			*/
			start = new Date();
			
			Database.replaceInventoryEntry(100100,ie);
			
			end = new Date();
			System.out.println(end.getTime()-start.getTime());
			
			start = new Date();
			
			Database.editAttributeOfInventoryEntry(500500, "count", "1");
			
			end = new Date();
			System.out.println(end.getTime()-start.getTime());
			
			start = new Date();
			
			Database.deleteInventoryEntry(100100);
			
			end = new Date();
			System.out.println(end.getTime()-start.getTime());
			
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}

		
		
		for (int i=0;i<categories.size();i++) {
			System.out.println(categories.get(i));
		}
		
		/*
		Category newCategory = new Category("New category");
		
		try {
			Database.addCategory(newCategory);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TextAreaLogProgram().setVisible(true);
            }
        });
		*/
	}
}