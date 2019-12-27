package model;

import model.interfaces.PositionInterface;

public class InventoryEntry implements PositionInterface{
	/*to-do:
	 * decide on UID (UniqueIDentifier);
	 * give shelfSection & shelfPlace better names.
	 * give getID better name.
	 *
	 */
	
	/**
    * Edit a InventoryEntry
    * @param UID edit Entry with this UID
    * @throws Exception in case of invalid value
    */
	private Integer shelfSection;
	private Integer shelfPlace;
	public Product product;
	
	public int getID() {
		return InventoryEntry.sectionPlaceToUID(shelfSection, shelfPlace);
	}
	
	public int getShelfSection() {
		return shelfSection;
	}
	
	public void setShelfSection(int shelfSection) {
		this.shelfSection = shelfSection;
	}
	
	
	public int getShelfPlace() {
		return shelfPlace;
	}
	
	public void setShelfPlace(int shelfPlace) {
		this.shelfPlace = shelfPlace;
	}
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public int getUID() {
		return this.getID();
	}
	
	public InventoryEntry(int shelfSection, int shelfPlace, Product product) {
		this.shelfSection=shelfSection;
		this.shelfPlace=shelfPlace;
		this.product=product;
	}
	
	@Deprecated
	public InventoryEntry(int shelfSection, int shelfPlace, int UID) {
		this.shelfSection=shelfSection;
		this.shelfPlace=shelfPlace;

	}
	
	@Deprecated
	public InventoryEntry(int shelfSection, int shelfPlace, int UID, Product product) {
		this.shelfSection=shelfSection;
		this.shelfPlace=shelfPlace;
		this.product=product;
	}
	
	public boolean validate() {
		if (this.shelfSection==null || this.shelfPlace==null || this.product.validate()==false) {
			return false;
		}
		return true;
	}
	
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
	

	static public int[] uidToSectionPlace(int UID){
		/* Idee ist als Eingabe UID zubekommen und dann Section und Place auszugeben.
		 * Java hat keinen sch�nen slice operator und auch nix geiles f�r length von int
		 */
		int[] sectionPlace= new int[2];
		if((int) (Math.log10(UID) + 1)!=6){
			return null;
		}
		String stringUID = Integer.toString(UID);
		String section=stringUID.substring(0, 3);
		String place=stringUID.substring(3, 6);
		//System.out.println("uidToSectionPlace("+UID+"): "+section+"|"+place);
		sectionPlace[0]=Integer.parseInt(section);
		sectionPlace[1]=Integer.parseInt(place);
		return sectionPlace;
	}
	
	static public int sectionPlaceToUID(int section, int place) {
		String sid = ""+section+place;
		int iid = Integer.parseInt(sid);
		return iid;
	}
	
	public String toString() {
		if(this.validate()) {
			return "InventoryEntry ("+this.getUID()+"):"+
					"\n\tShelfSection :"+this.getShelfSection()+
					"\n\tShelfPlace :"+this.getShelfPlace()+
					"\n\t\tproduct_name:"+this.product.getName()+
					"\n\t\tproduct_count:"+this.product.getCount()+
					"\n\t\tproduct_weight:"+this.product.getWeight()+"g"+
					"\n\t\tproduct_prize:"+this.product.getPrize()+"c"+
					"\n\tposition:"+this.getID()+
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
	
	public Object[] toObjectArray() {
		if (!this.validate()) {
			return null;
		}
		
		Object[] returnArray = { this.getShelfSection(), this.getShelfPlace(), this.product, this.product.getCategoryID()};
		return returnArray;
	}
}
