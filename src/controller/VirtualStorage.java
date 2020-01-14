package controller;

import java.util.ArrayList;

import database.Database;
import model.InventoryEntry;
import model.Product;

public class VirtualStorage {
	ArrayList<InventoryEntry> invEntry;
	
	public VirtualStorage() {
		loadVirtualStorage();
	}
	// optimize: delete from list??
	public InventoryEntry getEntryByID(int id) {
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getID()==id)
				return invEntry.get(i);
		} return null;
	}
	//TODO : levinson search
	//TODO : filter functions
	//TODO : sorting functions
	public int changeAmountBy(int id,int val) throws Exception {
		int nVal = getEntryByID(id).getProduct().getCount()+val;
		return setAmount(id,nVal);
	}
	public void loadVirtualStorage() {
		invEntry = Database.retrieveInventoryEntries();
	}
	//TODO : errorcodes
	public void addProduct(String name,int count,int weight,int prize,int cathid,int section,int place) throws Exception {
		Database.addInventoryEntry(new InventoryEntry(section,place,new Product(name,count,weight,prize,cathid)));
		loadVirtualStorage();
	}
	//TODO : error callback from database
	public void deleteProduct(int id) throws Exception {
		Database.deleteInventoryEntry(id, true);
		loadVirtualStorage();
	}
	//TODO : cathegory functions
	public int setName(int id,String name) throws Exception {
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getProduct().getName().equals(name))
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
	
	public ArrayList<InventoryEntry> getAllEntries() { return invEntry; }
}