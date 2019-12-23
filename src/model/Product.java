package model;

public class Product {
	@Deprecated
	private int id;
	private String name;
	private Integer weight;
	private Integer prize;
	private Integer count;
	private Integer categoryID;
	public Category category;
	
	@Deprecated
	public int getId() {
		return id;
	}
	@Deprecated
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWeight() {
		return weight;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getPrize() {
		return prize;
	}
	public void setPrize(int prize) {
		this.prize = prize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	@Deprecated
	public Product(int id, String name, int count){
		this.id=id;
		this.name=name;
		this.count=count;
	}
	
	public Product(String name, int count, int weight, int prize){
		this.name=name;
		this.count=count;
		this.weight=weight;
		this.prize=prize;
	}
	
	public Product(String name, int count, int weight, int prize, int categoryID){
		this.name=name;
		this.count=count;
		this.weight=weight;
		this.prize=prize;
		//to-do: append category
	}
	public boolean validate() {
		if (this.name==null || this.count==null || this.weight==null || this.prize==null /*|| this.categoryID==null*/) {
			return false;
		}
		return true;
	}
}
