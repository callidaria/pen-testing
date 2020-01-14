package model;

/** Die Inventareintrags-Klasse ist eine Basisklasse.
 *  Inventareinträge stellen Inventareinträge in unserer Datenbank dar.
 *  Dabei besitzt ein Inventareinträge ein Produkt. (Um den Inventareintrag nicht zu überladen) 
 * 
 * @author Freddy
 *
 */
public class InventoryEntry{

	//Die ersten 3 Zahlen der Platznummer
	private Integer shelfSection;
	
	//Die letzten 3 Zahlen der Platznummer
	private Integer shelfPlace;
	
	//Das Produkt, welches an diesem Platz gelagert wird.
	public Product product;
	
	/**
	 * 
	 * @return die UID (gesamte Platznummer)
	 */
	public int getUID() {
		return InventoryEntry.sectionPlaceToUID(shelfSection, shelfPlace);
	}
	
	/**
	 * 
	 * @return die UID mit führenden Nullen (z.B. 001001)
	 */
	public String getStringifiedUID() {
		String area = Integer.toString(this.getShelfSection());
		String place = Integer.toString(this.getShelfPlace());
		while (area.length()<3) {
			area="0"+area;
		}
		while (place.length()<3) {
			place="0"+place;
		}
		return area+place;
	}
	
	/**
	 * 
	 * @return die ersten 3 Zahlen der gesamten Platznummer aka. Regalnummer.
	 */
	public int getShelfSection() {
		return shelfSection;
	}
	
	/**Weißt shelfSection neu zu.
	 * 
	 * @param shelfSection
	 */
	public void setShelfSection(int shelfSection) {
		this.shelfSection = shelfSection;
	}
	
	/**
	 * 
	 * @return die letzten 3 Zahlen der gesamten Platznummer aka Regalplatznummer.
	 */
	public int getShelfPlace() {
		return shelfPlace;
	}
	
	/**Weißt shelfPlace neu zu.
	 * 
	 * @param shelfPlace
	 */
	public void setShelfPlace(int shelfPlace) {
		this.shelfPlace = shelfPlace;
	}
	
	/**Optional, da Produkt public ist.
	 * 
	 * @return das zugewiesen Produkt
	 */
	public Product getProduct() {
		return product;
	}
	
	/**
	 * 
	 * @param product, wenn dem Inventareintrag ein anderes Produkt zugeordnet werden soll.
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**Statische Version von {@link #getStringifiedUID()}.
	 * 
	 * @param uid, eine 4 bis 6 stellige gesamte Platznummer
	 * @return die UID mit führenden Nullen (z.B. 001001)
	 */
	public static String stringifyUID(int uid) {
		//StringUID
		String suid = Integer.toString(uid);
		while (suid.length()<6) {
			suid="0"+uid;
		}
		return suid;
	}
	
	/**Der einzige Konstruktor von Inventareintrag, damit immer Regalnummer, Regalplatznummer und ein Produkt zugewiesen sein muss.
	 * 
	 * @param shelfSection Regalnummer
	 * @param shelfPlace Regalplatznummer
	 * @param product Produkt, welches an dieser Stelle gelagert wird.
	 */
	public InventoryEntry(int shelfSection, int shelfPlace, Product product) {
		this.shelfSection=shelfSection;
		this.shelfPlace=shelfPlace;
		this.product=product;
	}
	
	/**Zum schnellen Prüfen ob ein Eintrag valide ist.
	 * 
	 * @return true, wenn kein Feld von einem Objekt dieser Klasse null ist und das Produkt auch validiert (@link Product#validate()).
	 */
	public boolean validate() {
		if (this.shelfSection==null || this.shelfPlace==null || this.product.validate()==false) {
			return false;
		}
		return true;
	}
	
	/**Experimentell, wird aktuell nicht in der Anwendung genutzt.
	 * 
	 * @return wahr, wenn der Eintrag repariert werden konnte.
	 */
	public boolean repair() {
		if(!this.validate()) {
			if (this.shelfSection==null) {
				this.setShelfSection(-1);
			}
			if (this.shelfPlace==null) {
				this.setShelfPlace(-1);
			}
			if(!this.product.validate()) {
				this.product.repair();
			}
		}
		else {
			return false;
		}
		
		return true;
	}
	

	/**Eine statische Methode um eine UID in Regalnummer und Regalplatznummer umzuwandeln.
	 * 
	 * @param UID
	 * @return int[2]: [0] Regalnummer, [1] Regalplatznummer.
	 */
	static public int[] uidToSectionPlace(int UID){
		// Idee ist als Eingabe UID zubekommen und dann Section und Place auszugeben.

		int[] sectionPlace= new int[2];
		
		String stringUID = Integer.toString(UID);
		
		int stringLength = stringUID.length();
		
		//Die gelieferte UID, sollte mindestens 4 Zahlen (z.B. "00"1000) haben und nicht mehr als 6 Zeichen
		if(stringLength>6||stringLength<4){
			return new int[] {-1,-1};
		}
		
		//Alle Zeichen vor den letzten 3.
		String section=stringUID.substring(0, stringLength - 3);
		
		//Die letzten 3 Zeichen.
		String place=stringUID.substring(stringLength - 3, stringLength);
		
		//System.out.println("uidToSectionPlace("+UID+"): "+section+"|"+place);
		//Regalnummer
		sectionPlace[0]=Integer.parseInt(section);
		//Regalplatznummer
		sectionPlace[1]=Integer.parseInt(place);
		
		return sectionPlace;
	}
	
	/**Wandelt Regalnummer und Regalplatznummer zu einer UID.
	 * 
	 * @param section, Regalnummer
	 * @param place, Regalplatznummer
	 * @return UID
	 */
	static public int sectionPlaceToUID(int section, int place) {
		String strPlace = Integer.toString(place);
		//Unsere Lösung für führende Nullen, verhindert dass die Regalnummer 0 ist.
		if(section<1||place<0) {
			return -1;
		}
		while (strPlace.length()<3) {
			strPlace="0"+strPlace;
		}
		String sid = ""+section+strPlace;
		int iid = Integer.parseInt(sid);
		return iid;
	}
	
	/**
	 * @return den Inventareintrag als lesbarer Stirng für die Konsole.
	 */
	public String toString() {
		if(this.validate()) {
			return "InventoryEntry ("+this.getUID()+"):"+
					"\n\tShelfSection :"+this.getShelfSection()+
					"\n\tShelfPlace :"+this.getShelfPlace()+
					"\n\t"+this.product+
					"\n\tvalide:"+this.validate();
		}
		if(this.product.validate()) {
			return "InventoryEntry ():"+
					"\n\t\tproduct_name:"+this.product.getName()+
					"\n\t\tproduct_count:"+this.product.getCount()+
					"\n\t\tproduct_weight:"+this.product.getWeight()+"g"+
					"\n\t\tproduct_prize:"+this.product.getPrize()+"c"+
					"\n\tvalide:"+this.validate();
		}
		if(this.getUID()!=0) {
			return "InventoryEntry ("+this.getUID()+"):"+
					"\n\tvalide:"+this.validate();
		}
		else {
			return "This InventoryEntry is not valid.";
		}
		
    }
	
	/**
	 * 
	 * @return Inventareintrag als Object Array, fürs Frontend eventuell.
	 */
	public Object[] toObjectArray() {
		if (!this.validate()) {
			return new  Object[] {null,null,null,null,null,null};
		}
		Object[] returnArray = {
				this.getStringifiedUID(),
				this.product.getName(),
				this.product.getCount(),
				this.product.getWeight(),
				this.product.getPrize(),
				this.product.category.getName()
				};
		return returnArray;
	}
}
