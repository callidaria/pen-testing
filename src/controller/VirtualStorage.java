package controller;

import java.util.ArrayList;
import java.util.List;

import database.Database;
import model.Category;
import model.InventoryEntry;
import model.Product;

public class VirtualStorage {
	List<InventoryEntry> invEntry;
	List<Category> cathEntry;
	public boolean loaded;
	
	public VirtualStorage() {
		loadVirtualStorage();
		loadCathegoryStorage();
	}
	//TODO ALL: delete from list ??optimize
	//TODO : javadoc
	public InventoryEntry getEntryByUID(int id) {
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getUID()==id)
				return invEntry.get(i);
		} return null;
	}
	public void getEntriesByLevinson(String search) {
		//TODO
	}
	public void filterByID(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getUID()>=from&&invEntry.get(i).getUID()<=to) nie.add(invEntry.get(i));
		} invEntry = nie;
	}
	public void filterBySection(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getShelfSection()>=from&&invEntry.get(i).getShelfSection()<=to) nie.add(invEntry.get(i));
		} invEntry = nie;
	}
	public void filterByPlace(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getShelfPlace()>=from&&invEntry.get(i).getShelfPlace()<=to) nie.add(invEntry.get(i));
		} invEntry = nie;
	}
	public void filterByCount(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getProduct().getCount()>=from&&invEntry.get(i).getProduct().getCount()<=to)
				nie.add(invEntry.get(i));
		} invEntry = nie;
	}
	public void filterByWeight(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getProduct().getWeight()>=from&&invEntry.get(i).getProduct().getWeight()<=to)
				nie.add(invEntry.get(i));
		} invEntry = nie;
	}
	public void filterByPrize(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getProduct().getWeight()>=from&&invEntry.get(i).getProduct().getWeight()<=to)
				nie.add(invEntry.get(i));
		} invEntry = nie;
	}
	public void filterByCathegory(Category cath) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getProduct().getCategoryID()==cath.getUID()) nie.add(invEntry.get(i));
		} invEntry = nie;
	}
	public void sortByID(boolean down) {
		//TODO
	}
	public void sortByName(boolean down) {
		//TODO
	}
	public void sortByCount(boolean down) {
		//TODO
	}
	public void sortByWeight(boolean down) {
		//TODO
	}
	public void sortByPrize(boolean down) {
		//TODO
	}
	public int changeAmountBy(int id,int val) throws Exception {
		int nVal = getEntryByUID(id).getProduct().getCount()+val;
		return setAmount(id,nVal);
	}
	public void loadVirtualStorage() {
		invEntry = Database.retrieveInventoryEntriesWithCategory(Database.retrieveCategories());
		loaded=true;
	}
	public void loadCathegoryStorage() {
		cathEntry = Database.retrieveCategories();
	}
    public int addProduct(int UID, String name, int count, int weight, int prize, int categoryID) throws Exception {
        int section = InventoryEntry.uidToSectionPlace(UID)[0];
        int place = InventoryEntry.uidToSectionPlace(UID)[1];
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getProduct().getName()==name) return -1;
			else if (section==invEntry.get(i).getShelfSection()&&place==invEntry.get(i).getShelfPlace()) return -2;
		} Database.addInventoryEntry(new InventoryEntry(section,place,new Product(name,count,weight,prize,categoryID)));
		loadVirtualStorage();
		return 0;
	}
	public void deleteProduct(int id) throws Exception {
		Database.deleteInventoryEntry(id, true);
		loadVirtualStorage();
	}
	//TODO : rename
	public int addCathegory(int id,String name) throws Exception {
		for (int i=0;i<cathEntry.size();i++) {
			if (cathEntry.get(i).getName()==name||cathEntry.get(i).getUID()==id) return -1; // ??id handler by fe
		} Database.addCategory(new Category(id,name));
		loadCathegoryStorage();
		return 0;
	}
	public int renameCathegory(int id, String newName) {
		for (int i=0;i<cathEntry.size();i++) {
			if (cathEntry.get(i).getName()==newName) return -1;
		} Database.renameCategory(id, newName);
		loadCathegoryStorage();
		return 0;
	}
	public void removeCathegory(int id) throws Exception{
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getProduct().getCategoryID()!=id) nie.add(invEntry.get(i));
		} invEntry = nie;
		Database.deleteCategory(id);
	}

	public int setName(int id,String name) throws Exception {
		for (int i=0;i<invEntry.size();i++) {
			if (invEntry.get(i).getProduct().getName().equals(name)) return -1;
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

	public List<InventoryEntry> getAllEntries() { return invEntry; }

	public List<Category> getAllCategories(){return cathEntry;}
    
    public Object[][] getInventoryEntryObjectArray() {
        List <InventoryEntry> ie = getAllEntries();
        
        List<Object[]> arrayList = new ArrayList<Object[]>();
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
    public Object[][] getCategoryObjectArray() {
        List <Category> categories = cathEntry;
        System.out.println(categories.get(0));
        List<Object[]> arrayList = new ArrayList<Object[]>();
        for(int i=0;categories.size()>i;i++) {
            arrayList.add(categories.get(i).toObjectArray());
        }
        
        Object[][] objectArray = new Object[categories.size()+10][10];
        for (int i=0;arrayList.size()>i;i++) {
            objectArray[i]=arrayList.get(i);
        }
        return objectArray;
    }
	public void replaceInventoryEntry(int uid, InventoryEntry editedIE) throws Exception{
		
		Database.replaceInventoryEntry(uid, editedIE);
		loadVirtualStorage();
	}
}