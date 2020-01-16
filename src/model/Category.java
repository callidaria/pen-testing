package model;

public class Category {
	private Integer uid;
	private String name;
	public Category(int uid, String name) {
		this.uid=uid;
		this.name = name;
	}
	public Category(String string) {
		this.uid = -1;
		this.name=string;
	}
	public int getUID() {
		return uid;
	}
	public void setUID(int uid) {
		//illegal operation
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Category ("+uid+"): "+name;
	}
	public boolean validate() {
		if (this.name==null || this.uid==null) {
			return false;
		}
		return true;
	}
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
