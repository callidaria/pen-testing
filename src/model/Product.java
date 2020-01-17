package model;

import java.util.ArrayList;

/**Die Produkt-Klasse ist Teil der Basisklasse Inventareintrag.
 * Produkte represäntieren ein Objekt welches an der Stelle des Inventareintrags gelagert werden.
 * Das Produkt ist eine eigenes Objekt um die agilität der Anwendung zu erhöhen.
 * Durch die Trennung des Eintrages und des Produktes, wäre ein einfacher wechsel auf ein 1-n Beziehung,
 * zwischen Platz und Produkt möglich gewesen.
 * 
 * @author Freddy
 *
 */
public class Product {

	//Alle Attribute eines Objektes Produkt
	private String name;
	private Integer weight;
	private Integer prize;
	private Integer count;
	private Integer categoryID;
	
	//Kategorie ist ein eine mit Hilfe der categoryID zugewiesene Klasse. Es handelt sich dabei um eine Aggregation und keine Komposition.
	public Category category;

	/**Getter/Setter-Methode
	 * 
	 * @return Name des Produktes
	 */
	public String getName() {
		return name;
	}
	
	/**Getter/Setter-Methode
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**Getter/Setter-Methode
	 * 
	 * @return Gewicht des Produktes
	 */
	public int getWeight() {
		return weight;
	}
	/**Gewicht so wie sie in der Anwendung dargestellt werden soll.
	 * Einheit ist Gramm, wenn weniger als 1 Kilo, ansonsten Kilogramm.
	 * 
	 * @return Gewicht mit Einheit als String
	 */
	public String getStringifiedWeight() {
		if(weight<1000) {
			float fWeight = (float)this.weight/10.0f;

			return String.valueOf(fWeight)+"g";
		}
		
		float fWeight = (float) this.weight/10000.0f;
		return String.valueOf(fWeight)+"kg";
	}
	
	/**Getter/Setter-Methode
	 * 
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**Getter/Setter-Methode
	 * 
	 * @return Preis in Cent
	 */
	public int getPrize() {
		return prize;
	}
	
	/**Getter/Setter-Methode
	 * 
	 * @param Preis in Cent
	 */
	public void setPrize(int prize) {
		this.prize = prize;
	}
	/**Preis so wie sie in der Anwendung dargestellt werden soll.
	 * Einheit ist Cent, wenn weniger als 1 Euro, sonst Euro.
	 * 
	 * @return Preis mit Einheit als String
	 */
	public String getStringifiedPrize() {
		if(this.prize<100) {
			return String.valueOf(prize)+"c";
		}
		float fPrize = (float) this.prize/100;
		return String.valueOf(fPrize)+"€";
	}
	
	/**Getter/Setter-Methode
	 * 
	 * @return Menge in Stück
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * 
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/**Getter/Setter-Methode
	 * 
	 * @return KategorieID, eine künstlich erzeugter Identifier
	 */
	public int getCategoryID() {
		return categoryID;
	}
	
	/**Getter/Setter-Methode
	 * Beim Neustart wird dann auch der Name der Kategorie angezeigt.
	 * 
	 * @param categoryID der gewünschten Kategorie.
	 */
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
		this.setCategory(new Category(categoryID,"Kategorie nicht geladen"));
	}
	
	/**Getter/Setter-Methode
	 * Weißt Kategorie neu zu.
	 * 
	 * @param category, die gewünschte Kategorie als Category Object.
	 */
	public void setCategory(Category category) {
		this.categoryID=category.getUID();
		this.category=category;
	}
	
	
	/** Konstruktor ohne den Namen der Kategorie
	 * 
	 * @param name Name
	 * @param count Anzahl in Stück
	 * @param weight Gewicht in dezigramm
	 * @param prize Preis in Cent
	 * @param categoryID KategorieID
	 */
	public Product(String name, int count, int weight, int prize, int categoryID){
		this.name=name;
		this.count=count;
		this.weight=weight;
		this.prize=prize;
		this.categoryID=categoryID;
		this.setCategory(new Category(categoryID,"Kategorie nicht geladen"));
	}
	/** Konstruktor mit Verweis auf Kategorie
	 * 
	 * @param name Name
	 * @param count Anzahl in Stück
	 * @param weight Gewicht in dezigramm
	 * @param prize Preis in Cent
	 * @param categoryID KategorieID
	 * @category category Verweis auf Kategorie
	 */
	public Product(String name, int count, int weight, int prize, int categoryID, Category category){
		this.name=name;
		this.count=count;
		this.weight=weight;
		this.prize=prize;
		this.categoryID=categoryID;
		this.category=category;
	}
	
	/**Ein schneller Check, ob irgendein Attribut null ist und ob die Kategore validiert.
	 * Kein ausführlicher Constraintcheck.
	 * 
	 * @return true, wenn kein Wert null
	 */
	public boolean validate() {
		if (this.name==null || this.count==null || this.weight==null || this.prize==null || this.categoryID==null || this.category.validate() == false) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		if(this.validate()) {
			return "Product:"+
					"\n\t\tproduct_name:"+this.getName()+
					"\n\t\tproduct_count:"+this.getCount()+
					"\n\t\tproduct_weight:"+this.getWeight()+"g"+
					"\n\t\tproduct_prize:"+this.getPrize()+"c"+
					"\n\t\t"+this.category;
		}
		return "Product:"+
		"\n\tvalidate:false";
	}
}
