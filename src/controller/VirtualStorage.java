package controller;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import database.Database;
import model.InventoryEntry;
import model.Product;
import model.Category;



public class VirtualStorage {
	List<InventoryEntry> inventoryEntry;
	List<Category> categoryEntry;
	public boolean loaded;
	
	public VirtualStorage() {
		loadVirtualStorage();
		loadCathegoryStorage();
	}
	//TODO ALL: delete from list ??optimize
	//TODO : javadoc
	public InventoryEntry getEntryByUID(int id) {
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getUID()==id)
				return inventoryEntry.get(i);
		} return null;
	}
	public void getEntriesByLevinson(String search) {
		//TODO
	}
	public void filterByID(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getUID()>=from&&inventoryEntry.get(i).getUID()<=to) nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	public void filterBySection(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getShelfSection()>=from&&inventoryEntry.get(i).getShelfSection()<=to) nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	public void filterByPlace(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getShelfPlace()>=from&&inventoryEntry.get(i).getShelfPlace()<=to) nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	public void filterByCount(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getCount()>=from&&inventoryEntry.get(i).getProduct().getCount()<=to)
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	public void filterByWeight(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getWeight()>=from&&inventoryEntry.get(i).getProduct().getWeight()<=to)
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	public void filterByPrize(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getWeight()>=from&&inventoryEntry.get(i).getProduct().getWeight()<=to)
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	public void filterByCathegory(Category cath) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getCategoryID()==cath.getUID()) nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	public int changeAmountBy(int id,int val) throws Exception {
		int nVal = getEntryByUID(id).getProduct().getCount()+val;
		return setAmount(id,nVal);
	}
	public void loadVirtualStorage() {
		inventoryEntry = Database.retrieveInventoryEntriesWithCategory(Database.retrieveCategories());
		loaded=true;
	}
	public void loadCathegoryStorage() {
		categoryEntry = Database.retrieveCategories();
	}
    public int addProduct(int UID, String name, int count, int weight, int prize, int categoryID) throws Exception {
        int section = InventoryEntry.uidToSectionPlace(UID)[0];
        int place = InventoryEntry.uidToSectionPlace(UID)[1];
        if (UID<0||UID>999999) return -2;
        else if (count<0||weight<0||prize<0) return -3;
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getName().compareTo(name)==0) return -1;
			else if (inventoryEntry.get(i).getUID()==UID) return -2;
		} Database.addInventoryEntry(new InventoryEntry(section,place,new Product(name,count,weight,prize,categoryID)));
		loadVirtualStorage();
		return 0;
	}
	public void deleteProduct(int id) throws Exception {
		Database.deleteInventoryEntry(id, true);
		loadVirtualStorage();
	}
	public int addCategory(int id,String name) throws Exception {
		for (int i=0;i<categoryEntry.size();i++) {
			if (categoryEntry.get(i).getName()==name||categoryEntry.get(i).getUID()==id) return -1; // ??id handler by fe
		} Database.addCategory(new Category(id,name));
		loadCathegoryStorage();
		return 0;
	}
	public int renameCategory(int id, String newName) {
		for (int i=0;i<categoryEntry.size();i++) {
			if (categoryEntry.get(i).getName()==newName) return -1;
		} Database.renameCategory(id, newName);
		loadCathegoryStorage();
		return 0;
	}
	public void removeCategory(int id) throws Exception{
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getCategoryID()!=id) nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
		Database.deleteCategory(id);
	}

	public int setAmount(int id,int amount) {
		if (amount<0) return -1;
		try {
			Database.editAttributeOfInventoryEntry(id,"count",String.valueOf(amount));
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadVirtualStorage();
		return 0;
	}

	public List<InventoryEntry> getAllEntries() { return inventoryEntry; }
	public List<Category> getAllCategories(){return categoryEntry;}
    
    public Object[][] getInventoryEntryObjectArray() {
        List <InventoryEntry> ie = getAllEntries();
        
        List<Object[]> arrayList = new ArrayList<Object[]>();
        for(int i=0;ie.size()>i;i++) {
            arrayList.add(ie.get(i).toObjectArray());
        }
        Object[][] objectArray = new Object[ie.size()][10];
        for (int i=0;arrayList.size()>i;i++) {
            objectArray[i]=arrayList.get(i);
        }
        return objectArray;
    }
    public Object[][] getCategoryObjectArray() {
        List <Category> categories = categoryEntry;
        System.out.println(categories.get(0));
        List<Object[]> arrayList = new ArrayList<Object[]>();
        for(int i=0;categories.size()>i;i++) {
            arrayList.add(categories.get(i).toObjectArray());
        }
        
        Object[][] objectArray = new Object[categories.size()][10];
        for (int i=0;arrayList.size()>i;i++) {
            objectArray[i]=arrayList.get(i);
        }
        return objectArray;
    }
	public int replaceInventoryEntry(int ouid, int nuid, String name, int amount, int weight, int prize, int cat_id) {
		if (amount<0||weight<0||prize<0) return -1;
		if (nuid<0||nuid>999999) return -2;
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getUID()==nuid&&ouid!=nuid) return -2;
			if (inventoryEntry.get(i).getProduct().getName().compareTo(name)==0&&inventoryEntry.get(i).getUID()!=ouid) return -3;
		} Database.replaceInventoryEntry(ouid,new InventoryEntry(nuid,new Product(name,amount,weight,prize,cat_id)));
		loadVirtualStorage();
		return 0;
	}
	
	public void manualSave() {
		Database.transformCategories();
		Database.transformInventoryEntries();
	}
	
	public boolean validateDatabase() throws SAXException {
		return Database.validate();
	}
}