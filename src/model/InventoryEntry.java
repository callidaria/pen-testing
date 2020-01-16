package model;

/** Die Inventareintrags-Klasse ist eine Basisklasse.
 *  Inventareinträge stellen Inventareinträge in unserer Datenbank dar.
 *  Dabei besitzt ein Inventareinträge ein Produkt. (Um den Inventareintrag nicht zu überladen) 
 * 
 * @author Freddy
 *
 */
public class InventoryEntry{

	/** Die Regalnummer. Die ersten 3 Zahlen der Platznummer.
	 */
	private Integer shelfSection;
	
	/**
	 * Die Regalplatznummer. Die letzten 3 Zahlen der Platznummer.
	 */
	private Integer shelfPlace;
	
	/**
	 * Das Produkt, welches an dem Platz diese EintragesS gelagert wird.
	 */
	public Product product;
	
	/**Getter/Setter-Methode
	 * 
	 * @return die UID (gesamte Platznummer) ohne führende Nullen.
	 */
	public int getUID() {
		return InventoryEntry.sectionPlaceToUID(shelfSection, shelfPlace);
	}
	
	/**Damit wir Zahlen mit führenden Nullen darstellen können, gibt es diese getStrigifiedUID Methode.
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
	
	/**Getter/Setter-Methode
	 * 
	 * @return die ersten 3 Zahlen der gesamten Platznummer aka. Regalnummer.
	 */
	public int getShelfSection() {
		return shelfSection;
	}
	
	/**Getter/Setter-Methode
	 * Weist shelfSection neu zu.
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
	
	/**Weist shelfPlace neu zu.
	 * 
	 * @param shelfPlace
	 */
	public void setShelfPlace(int shelfPlace) {
		this.shelfPlace = shelfPlace;
	}
	
	/**Getter/Setter-Methode
	 * Optional, da Produkt public ist.
	 * 
	 * @return das zugewiesen Produkt
	 */
	public Product getProduct() {
		return product;
	}
	
	/**Getter/Setter-Methode
	 * Optional, da Produkt public ist.
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
			suid="0"+suid;
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
		String section;
		String place;
		
		//Die gelieferte UID, sollte mindestens 0 sein und nicht mehr als 999999 Zeichen
		if(UID>999999||UID<0) {
			return new int[] {-1,-1};	
		}
		//Wenn die Zahl weniger als 4 Zeichen hat, dann ist die Regalnummer 0
		else if(stringLength<4){
			section = "000";
			place = stringUID;
		}
		//Ansonsten sind die letzten 3 Zeichen, die Regalplatznummer und alles davor die Regalnummer
		else {
			//Alle Zeichen vor den letzten 3.
			section=stringUID.substring(0, stringLength - 3);
			
			//Die letzten 3 Zeichen.
			place=stringUID.substring(stringLength - 3, stringLength);
		}		
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
		//Regalnummer und Regalplatznummer müssen positiv und kleiner als 1000 sein.
		if(section<0||place<0||section>999||place>999) {
			return -1;
		}
		while (strPlace.length()<3) {
			strPlace="0"+strPlace;
		}
		//StringID
		String sid = ""+section+strPlace;
		//IntegerID
		int iid = Integer.parseInt(sid);
		return iid;
	}
	
	/**Eine toString() Methode um ein Inventareintrag per print auszugeben.
	 * 
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
	
	/** Gibt den Inventareintrag als Object Array zurück. Insbesonders für das Frontend wichtig, z.B. Daten für eine JTable.
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
