import database.Database;

public class Main {
	public static void main(String[] args) {
		Database database= new Database();
		//String WOW = database.testQuery();
		//System.out.println(WOW);
		database.fetchStoredProducts(null);
		database.searchStoredProducts("PenXL");
	}
}
