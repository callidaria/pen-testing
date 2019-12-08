package controller;

import java.util.ArrayList;

import database.xml.Database;
import model.InventoryEntry;
import model.Product;

public class VirtualStorage {
	ArrayList<InventoryEntry> ie;
	ArrayList<Product> prod;
	
	public VirtualStorage() {
		ie=Database.retrieveInventoryEntries();
		prod=Database.retrieveProducts();
	}
	
	public InventoryEntry search_entry_pid(int id) {
		for(int i=0;i<ie.size();i++) {
			if (ie.get(i).getProductID()==id)
				return ie.get(i);
		} return null;
	}
}