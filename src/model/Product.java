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
	
	public String getStringifiedWeight() {
		if(weight<1000) {
			float fWeight = (float)this.weight/10.0f;

			return String.valueOf(fWeight)+"g";
		}
		
		float fWeight = (float) this.weight/10000.0f;
		return String.valueOf(fWeight)+"kg";
	}
	
	/**
	 * 
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPrize() {
		return prize;
	}
	
	/**
	 * 
	 * @param prize
	 */
	public void setPrize(int prize) {
		this.prize = prize;
	}
	
	public String getStringifiedPrize() {
		if(this.prize<100) {
			return String.valueOf(prize)+"c";
		}
		float fPrize = (float) this.prize/100;
		return String.valueOf(fPrize)+"€";
	}
	
	/**
	 * 
	 * @return
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
	
	/**
	 * Getter/Setter-Methode
	 * @param categoryID
	 */
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
		this.setCategory(new Category(categoryID,"Kategorie nicht geladen"));
	}
	
	public void setCategory(Category category) {
		this.categoryID=category.getUID();
		this.category=category;
	}
	
	
	
	public Product(String name, int count, int weight, int prize, int categoryID){
		this.name=name;
		this.count=count;
		this.weight=weight;
		this.prize=prize;
		this.categoryID=categoryID;
		this.setCategory(new Category(categoryID,"Kategorie nicht geladen"));
	}
	
	public Product(String name, int count, int weight, int prize, int categoryID, Category category){
		this.name=name;
		this.count=count;
		this.weight=weight;
		this.prize=prize;
		this.categoryID=categoryID;
		this.category=category;
	}
	
	
	public boolean validate() {
		if (this.name==null || this.count==null || this.weight==null || this.prize==null || this.categoryID==null || this.category.validate() == false) {
			return false;
		}
		return true;
	}
	public void repair() {
		// TODO Auto-generated method stub
		
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
