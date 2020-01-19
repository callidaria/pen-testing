package basic;

/** Kategorien stellen Kategorien in unserer Datenbank dar.
 * 
 * @author Freddy
 *
 */
public class Category {
	
	/**
	 * Ein künstlicher Identifier.
	 */
	private Integer uid;
	/**
	 * Der Name der Kategorie.
	 */
	private String name;
	
	/**Konstruktor mit UID und Name. Eine Kategorie kann nicht ohne das existieren.
	 * 
	 * @param uid Unique Identifier
	 * @param name Name
	 */
	public Category(int uid, String name) {
		this.uid=uid;
		this.name = name;
	}
	
	/**Getter/Setter-Methode Gibt UID
	 * 
	 * @return UID
	 */
	public int getUID() {
		return uid;
	}

	/**Getter/Setter-Methode Gibt Name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**Getter/Setter-Methode setzt Name
	 * 
	 * @param name der gewünschte Name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** Gibt Kategorie als String aus im Format:
	 * Category (uid): name 
	 */
	public String toString() {
		return "Category ("+uid+"): "+name;
	}
	
	/**Schnell Prüfung um Nullpointer exceptions zu verhindern.
	 * 
	 * @return true, wenn kein Attribute null ist.
	 */
	public boolean validate() {
		if (this.name==null || this.uid==null) {
			return false;
		}
		return true;
	}
	
	/**Gibt die Kategorie als Object Array aus. Insbesonders fürs Frontend wichtig.
	 * 
	 * @return Kategorie als Object array: {uid,name}
	 */
	public Object[] toObjectArray() {
		if (!this.validate()) {
			return new  Object[] {null,null};
		}
		Object[] returnArray = {
				this.getUID(),
				this.getName(),
				};
		return returnArray;
	}
	
}
