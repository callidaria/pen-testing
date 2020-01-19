package controller;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.SAXException;

import basic.Category;
import basic.InventoryEntry;
import basic.Product;
import database.Database;

/**
 * VirtualStorage ist die Klasse die es für das Frontend möglich macht einfach mit den Einträgen der Datenbank umzugehen.
 * Sie beinhaltet die Funktionen die maßgeschneidert auf die angestrebten Funktionen im Frontend sind und verhindert,
 * dass alle Einträge immer aus der Datenbank gelesen werden müssen.
 * 
 * @author Lovis Trüstedt
 * 
 */
public class VirtualStorage {
	List<InventoryEntry> inventoryEntry;
	List<Category> categoryEntry;
	public boolean loaded;
	
	/**
	 * Der Konstruktor lädt ersteinmal die Datenbank und alle Kategorien
	 */
	public VirtualStorage() {
		loadVirtualStorage();
		loadCategoryStorage();
	}
	/**
	 * Sucht den Eintrag mit der eingegebenen ID und gibt das zugehörige Objekt dazu aus
	 * @param id, spezifiziert die ID des gesuchten Eintrags
	 * @return das gesuchte Objekt des Datenbankeintrages
	 */
	public InventoryEntry getEntryByUID(int id) {
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getUID()==id)
				return inventoryEntry.get(i);
		} return null;
	}
	/**
	 * Sucht jedes Attribut jedes Eintrags nach Äquivalenzen mit dem eingegebenen Suchwort ab
	 * @param search, das Suchwort
	 */
	public void searchEntries(String search) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			InventoryEntry proc = inventoryEntry.get(i);
			if (proc.getStringifiedUID().contains(search)
					||proc.getProduct().getName().toLowerCase().contains(search.toLowerCase())
					||Integer.toString(proc.getProduct().getCount()).contains(search)
					||Integer.toString(proc.getProduct().getWeight()).contains(search)
					||Integer.toString(proc.getProduct().getPrize()).contains(search)
					||getCategoryNameByID(proc.getProduct().getCategoryID()).toLowerCase().contains(search.toLowerCase()))
				nie.add(proc);
		} inventoryEntry = nie;
	}
	/**
	 * Sucht die ID jedes Eintrags nach Äquivalenzen mit dem eingegebenen Suchwort ab
	 * @param search, das Suchwort
	 */
	public void searchEntriesByID(String search) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getStringifiedUID().contains(search))
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Sucht den Namen jedes Eintrags nach Äquivalenzen mit dem eingegebenen Suchwort ab
	 * @param search, das Suchwort
	 */
	public void searchEntriesByName(String search) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getName().toLowerCase().contains(search.toLowerCase()))
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Sucht die Menge jedes Eintrags nach Äquivalenzen mit dem eingegebenen Suchwort ab
	 * @param search, das Suchwort
	 */
	public void searchEntriesByAmount(String search) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (Integer.toString(inventoryEntry.get(i).getProduct().getCount()).contains(search))
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Sucht das Gewicht jedes Eintrags nach Äquivalenzen mit dem eingegebenen Suchwort ab
	 * @param search, das Suchwort
	 */
	public void searchEntriesByWeight(String search) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (Integer.toString(inventoryEntry.get(i).getProduct().getWeight()).contains(search))
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Sucht den Preis jedes Eintrags nach Äquivalenzen mit dem einegegebenen Suchwort ab
	 * @param search, das Suchwort
	 */
	public void searchEntriesByPrize(String search) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (Integer.toString(inventoryEntry.get(i).getProduct().getPrize()).contains(search))
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Sucht den String-Wert der Kategorie jedes Eintrags nach Äquivalenzen mit dem eingegebenen Suchwort ab
	 * @param search, das Suchwort
	 */
	public void searchEntriesByCategory(String search) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (getCategoryNameByID(inventoryEntry.get(i).getProduct().getCategoryID()).toLowerCase().contains(search.toLowerCase()))
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Hilfsmethode die anhand einer eingegebenen Kategorie-ID den zugehörigen Namen als String ausgibt
	 * @param ID, Kategorie-ID
	 * @return der String-Wert des gesuchten Kategorienamens
	 */
	private String getCategoryNameByID(int ID) {
		for (int i=0;i<categoryEntry.size();i++) {
			if (categoryEntry.get(i).getUID()==ID) return categoryEntry.get(i).getName();
		}
		return null;
	}
	/**
	 * Sucht den Namen jeder Kategorie nach Äquivalenzen mit dem eingegebenen Suchwort ab
	 * @param search, das Suchwort
	 */
	public void searchCategoriesByName(String search) {
		loadCategoryStorage();
		List<Category> nce = new ArrayList<Category>();
		for (int i=0;i<categoryEntry.size();i++) {
			if (categoryEntry.get(i).getName().toLowerCase().contains(search.toLowerCase()))
				nce.add(categoryEntry.get(i));
		} categoryEntry = nce;
	}
	/**
	 * Verbesserung der Anzeigereihenfolge von kurzen Listen anhand eines Suchwortes mithilfe der Levenshtein Abweichung
	 * @param search, das Suchwort
	 */
	public void searchEntriesByLevenshtein(String search) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		List<Integer> weights = new ArrayList<Integer>();
		for (int i=0;i<inventoryEntry.size();i++) {
			System.out.println(i);
			int res = levenshtein(inventoryEntry.get(i).getProduct().getName(),search);
			if (nie.size()==0) {
				nie.add(inventoryEntry.get(i));
				weights.add(res);
			} else {
				for (int j=0;j<nie.size();j++) {
					if (weights.get(j)<=res) {
						nie.add(j,inventoryEntry.get(i));
						weights.add(j,res);
						break;
					}
				}
			}
		}
		inventoryEntry = nie;
	}
	/**
	 * Rekursive Hilfsmethode zur Berechnung der Levenshtein Abweichung zweier Strings
	 * @param i, erster String
	 * @param j, zweiter String
	 * @return Resultat der Levenshtein Abweichung
	 */
	private int levenshtein(String i, String j) {
		int is = i.length(); int js = j.length();
		if (Math.min(is,js)==0) return Math.max(is,js);
		return Math.min(Math.min(levenshtein(i.substring(0,is-1),j)+1,
				levenshtein(i,j.substring(0,js-1))+1),
				levenshtein(i.substring(0,is-1),j.substring(0,js-1))+((i.substring(is-1).compareTo(j.substring(js-1))!=0)?1:0));
	}
	/**
	 * Filter einer bestimmten Wertespanne, entschieden nach Wert der ID
	 * @param from, erster angezeigter Wert
	 * @param to, letzter angezeigter Wert
	 */
	public void filterByID(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getUID()>=from&&inventoryEntry.get(i).getUID()<=to) nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Filter einer bestimmten Wertespanne, entschieden nach der Regalnummer
	 * @param from, erster angezeigter Wert
	 * @param to, letzter angezeigter Wert
	 */
	public void filterBySection(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getShelfSection()>=from&&inventoryEntry.get(i).getShelfSection()<=to) nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Filter einer bestimmten Wertespanne, entschieden nach der Platznummer
	 * @param from, erster angezeigter Wert
	 * @param to, letzter angezeigter Wert
	 */
	public void filterByPlace(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getShelfPlace()>=from&&inventoryEntry.get(i).getShelfPlace()<=to) nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Filter einer bestimmten Wertespanne, entschieden nach Anzahl der Lagernden Objekte
	 * @param from, erster angezeigter Wert
	 * @param to, letzter angezeigter Wert
	 */
	public void filterByCount(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getCount()>=from&&inventoryEntry.get(i).getProduct().getCount()<=to)
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Filter einer bestimmten Wertespanne, entschieden nach Artikelgewicht
	 * @param from, erster angezeigter Wert
	 * @param to, letzter angezeigter Wert
	 */
	public void filterByWeight(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getWeight()>=from&&inventoryEntry.get(i).getProduct().getWeight()<=to)
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Filter einer bestimmten Wertespanne, entschieden nach Artikelpreis
	 * @param from, erster angezeigter Wert
	 * @param to, letzter angezeigter Wert
	 */
	public void filterByPrize(int from, int to) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getWeight()>=from&&inventoryEntry.get(i).getProduct().getWeight()<=to)
				nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Filter einer bestimmten Wertespanne, entschieden nach Wert der Kategorie-ID
	 * @param from, erster angezeigter Wert
	 * @param to, letzter angezeigter Wert
	 */
	public void filterByCathegory(Category cath) {
		List<InventoryEntry> nie = new ArrayList<InventoryEntry>();
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getCategoryID()==cath.getUID()) nie.add(inventoryEntry.get(i));
		} inventoryEntry = nie;
	}
	/**
	 * Änderung des Lagerungswertes
	 * @param id, betroffener Eintrag
	 * @param val, Werteveränderung
	 * @return errorcodes
	 * @throws Exception
	 */
	public int changeAmountBy(int id,int val) throws Exception {
		int nVal = getEntryByUID(id).getProduct().getCount()+val;
		return setAmount(id,nVal);
	}
	/**
	 * Lädt die Einträge aus der Datenbank in die Liste
	 */
	public void loadVirtualStorage() {
		inventoryEntry = Database.retrieveInventoryEntriesWithCategory(Database.retrieveCategories());
		loaded=true;
	}
	/**
	 * Lädt die Kategorien aus der Datenbank in die Liste
	 */
	public void loadCategoryStorage() {
		categoryEntry = Database.retrieveCategories();
	}
	/**
	 * Fügt ein Artikel hinzu
	 * @param UID, neue ID
	 * @param name, Artikelname
	 * @param count, Artikelanzahl
	 * @param weight, Artikelgewicht
	 * @param prize, Artikelpreis
	 * @param categoryID, Kategorie
	 * @return errorcode
	 * @throws Exception
	 */
    public int addProduct(int UID, String name, int count, int weight, int prize, int categoryID) throws Exception {
        int section = InventoryEntry.uidToSectionPlace(UID)[0];
        int place = InventoryEntry.uidToSectionPlace(UID)[1];
        int sw = getCurrentShelfWeight(UID/(int)1000);
        if (sw+count*weight>100000000) return -4;
        if (name.length()>255) return -1;
        if (UID<0||UID>999999) return -2;
        if (count<0||weight<0||prize<0) return -3;
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getName().compareTo(name)==0) return -1;
			else if (inventoryEntry.get(i).getUID()==UID) return -2;
		} Database.addInventoryEntry(new InventoryEntry(section,place,new Product(name,count,weight,prize,categoryID)));
		loadVirtualStorage();
		return 0;
	}
    /**
     * Löscht ein Artikel
     * @param id, Zugehörige ID
     * @throws Exception
     */
	public void deleteProduct(int id) throws Exception {
		Database.deleteInventoryEntry(id, true);
		loadVirtualStorage();
	}
	/**
	 * Fügt neue Kategorie hinzu
	 * @param name, Name der Kategorie
	 * @return errorcode
	 */
	public int addCategory(String name) {
		if (name.length()>255) return -1;
		for (int i=0;i<categoryEntry.size();i++) {
			if (categoryEntry.get(i).getName().compareTo(name)==0) return -1;
		} try {
			Database.addCategory(name);
			loadCategoryStorage();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -2;
		}
	}
	/**
	 * Benennt Kategorie um
	 * @param id, ID der Katogorie
	 * @param newName, Neuer Name
	 * @return errorcode
	 */
	public int renameCategory(int id, String newName) {
		for (int i=0;i<categoryEntry.size();i++) {
			if (categoryEntry.get(i).getName().compareTo(newName)==0) return -1;
		} Database.renameCategory(id, newName);
		loadCategoryStorage();
		return 0;
	}
	/**
	 * Löscht Kategorie
	 * @param id, ID der Katogorie
	 * @return errorcode
	 */
	public int removeCategory(int id) {
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getProduct().getCategoryID()==id) return -1;
		} try {
			Database.deleteCategory(id);
		} catch (Exception e) {
			e.printStackTrace();
		} loadCategoryStorage();
		return 0;
	}
	/**
	 * Eine Artikelmenge setzen
	 * @param id, Betroffene ArtikelID
	 * @param amount, Menge
	 * @return errorcode
	 */
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
    
	public Category getCategoryByUID(int id) {
		for (int i=0;i<categoryEntry.size();i++) {
			if (categoryEntry.get(i).getUID()==id)
				return categoryEntry.get(i);
		} return null;
	}
	
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
		int sw = getCurrentShelfWeight((nuid)/(int)1000);
		if (sw+amount*weight>100000000) return -4;
		if (name.length()>255) return -3;
		if (amount<0||weight<0||prize<0) return -1;
		if (nuid<0||nuid>999999) return -2;
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getUID()==nuid&&ouid!=nuid) return -2;
			if (inventoryEntry.get(i).getProduct().getName().compareTo(name)==0&&inventoryEntry.get(i).getUID()!=ouid) return -3;
		} Database.replaceInventoryEntry(ouid,new InventoryEntry(nuid,new Product(name,amount,weight,prize,cat_id)));
		loadVirtualStorage();
		return 0;
	}
	private int getCurrentShelfWeight(int shelf) {
		int result = 0;
		for (int i=0;i<inventoryEntry.size();i++) {
			if (inventoryEntry.get(i).getShelfSection()==shelf)
				result+=inventoryEntry.get(i).getProduct().getCount()*inventoryEntry.get(i).getProduct().getWeight();
		} return result;
	}
	public void manualSave() {
		Database.transformCategories();
		Database.transformInventoryEntries();
	}
	
	public boolean validateDatabase() throws SAXException {
		return Database.validate();
	}
}