package database;

/**Experimentelle Klasse.
 * 
 * @author Freddy
 *
 */
public class DatabaseErrors {
	public static final String uidTaken = "Der Platz ist bereits belegt";
	public static final String nameTaken = "Der Name ist bereits belegt";
	public static String inventoryEntryFailedValidation="New InventoryEntry failed validation.";
	public static String internalWarning = "Entschuldigung, es handelt sich hierbei um einen internen Fehler. Wenden Sie sich an einen Admin.";
	public static String nameTaken(String name) {
		return "Der Name ("+name+") ist bereits belegt";
	}
	public static String uidTaken(int uid) {
		return "Der Platzt ("+uid+") ist bereits belegt";
	}
}
