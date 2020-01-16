package controller;

import java.util.ArrayList;

import database.Database;
import model.Category;
import model.InventoryEntry;
import model.Product;

public class VirtualStorage {
	ArrayList<InventoryEntry> inventoryEntries;
	ArrayList<Category> categories;
	
	public VirtualStorage() {
		loadVirtualStorage();
	}
	// optimize: delete from list??
	public InventoryEntry getEntryByUID(int id) {
		for (int i=0;i<inventoryEntries.size();i++) {
			if (inventoryEntries.get(i).getUID()==id)
				return inventoryEntries.get(i);
		} return null;
	}
	//TODO : levinson search
	//TODO : filter functions
	//TODO : sorting functions
	public int changeAmountBy(int id,int val) throws Exception {
		int nVal = getEntryByUID(id).getProduct().getCount()+val;
		return setAmount(id,nVal);
	}
	public void loadVirtualStorage() {
		categories = Database.retrieveCategories();
		inventoryEntries = Database.retrieveInventoryEntriesWithCategory(categories);
	}
	//TODO : errorcodes
	public void addProduct(int UID, String name, int count, int weight, int prize, int categoryID) throws Exception {
		int section = InventoryEntry.uidToSectionPlace(UID)[0];
		int place = InventoryEntry.uidToSectionPlace(UID)[1];
		Database.addInventoryEntry(new InventoryEntry(section,place,new Product(name,count,weight,prize,categoryID)));
		loadVirtualStorage();
	}
	//TODO : error callback from database
	public void deleteProduct(int id) throws Exception {
		Database.deleteInventoryEntry(id, true);
		loadVirtualStorage();
	}
	//TODO : cathegory functions
	public int setName(int id,String name) throws Exception {
		for (int i=0;i<inventoryEntries.size();i++) {
			if (inventoryEntries.get(i).getProduct().getName().equals(name))
				return -1;
		}
		Database.editAttributeOfInventoryEntry(id,"name",name);
		loadVirtualStorage();
		return 0;
	}
	public int setAmount(int id,int amount) throws Exception {
		if (amount<0) return -1;
		Database.editAttributeOfInventoryEntry(id,"count",String.valueOf(amount));
		loadVirtualStorage();
		return 0;
	}
	public int setWeight(int id,int weight) throws Exception {
		if (weight<0) return -1;
		Database.editAttributeOfInventoryEntry(id,"weight",String.valueOf(weight));
		loadVirtualStorage();
		return 0;
	}
	public int setPrize(int id,int prize) throws Exception {
		if (prize<0) return -1;
		Database.editAttributeOfInventoryEntry(id,"prize",String.valueOf(prize));
		loadVirtualStorage();
		return 0;
	}
	//TODO : shelf and place setters
	
	public ArrayList<InventoryEntry> getAllEntries() { return inventoryEntries; }
	
	
	public Object[][] getObjectArray() {
		ArrayList <InventoryEntry> ie = getAllEntries();
        
        ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
        for(int i=0;ie.size()>i;i++) {
        	arrayList.add(ie.get(i).toObjectArray());
        	
        }
        arrayList.get(0);
        Object[][] objectArray = new Object[ie.size()+10][10];
        for (int i=0;arrayList.size()>i;i++) {
        	objectArray[i]=arrayList.get(i);
        }
        return objectArray;
	}
}