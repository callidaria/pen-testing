package controller;

import java.util.ArrayList;

import database.Database;
import model.InventoryEntry;
import model.Product;

public class VirtualStorage {
	ArrayList<InventoryEntry> invEntry;
	ArrayList<Product> prod;
	
	public VirtualStorage() {
		invEntry=Database.retrieveInventoryEntries();
		prod=Database.retrieveProducts();
	}
	
	public InventoryEntry getEntryByPID(int id) {
		for(int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getUID()==id)
				return invEntry.get(i);
		} return null;
	}
}