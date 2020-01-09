package database.xml;

public class DatabaseSchema {
	
	//**Der relative Pfad von root zu den Datenbankdateien
	static final String DBPATH="data/xml/";
	
	//Dateiname von Inventareinträgen	
	static final String DBPATH_IE="inventoryEntries.xml";
	
	//Dateiname der Definitionsdatei von Inventareinträgen
	static final String DBPATH_IE_XSD="inventoryEntries.xsd";
	
	//Dateiname von Kategorien	
		static final String DBPATH_CAT="categories.xml";
		
	//Dateiname der Definitionsdatei von Kategorien
	static final String DBPATH_CAT_XSD="categories.xsd";
	
	public static String[] elements = new String[] {"name","count","weight","prize","category_id"};
	public static String[] attributes = new String[] {"place","area"};
	public static String[] idTypes = new String[] {"place","area"};
	public static String[] intTypes = new String[] {"count","weight","prize","category_id"};
	
	
	public static boolean verifyAttribute(String attributetype, String value) {
		
		return true;
	}
}
