package database.xml;

public class DatabaseSchema {
	public static String[] elements = new String[] {"name","count","weight","prize","category_id"};
	public static String[] attributes = new String[] {"place","section"};
	public static String[] idTypes = new String[] {"place","section"};
	public static String[] intTypes = new String[] {"count","weight","prize","category_id"};
	
	
	public static boolean verifyAttribute(String attributetype, String value) {
		
		return true;
	}
}
