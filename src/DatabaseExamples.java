import java.util.ArrayList;
import java.util.Date;

import basic.Category;
import basic.InventoryEntry;
import basic.Product;
import database.Database;

public class DatabaseExamples{
	public DatabaseExamples() {		
		
		Date start;
		Date end;
		
		
		start = new Date();
		try {
			Database.addTestInventoryEntries(100, 999);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		end = new Date();
		System.out.println(end.getTime()-start.getTime());
		
		if(true)return;
		
		ArrayList<Category> categories = Database.retrieveCategories();

		ArrayList<InventoryEntry> inventoryEntries = Database.retrieveInventoryEntriesWithCategory(categories);

		
		Product pro = new Product("Pen 2", 12, 12, 99, 1);
		InventoryEntry ie = new InventoryEntry(100,100,pro);

		try {
			System.out.println("Database status: "+Database.validate());

			Database.replaceInventoryEntry(100100,ie);

			Database.editAttributeOfInventoryEntry(500500, "count", "1");
			
			Database.deleteInventoryEntry(100100);			
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}

		/*
		for (int i=0;i<inventoryEntries.size();i++) {
			System.out.println(inventoryEntries.get(i));
		}
		*/
		
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