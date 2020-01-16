import controller.VirtualStorage;

public class Main {
	public static void main(String[] args) {
		VirtualStorage vs = new VirtualStorage();
		for(int i=0;i<vs.getAllEntries().size();i++)
			System.out.println(vs.getAllEntries().get(i));
		vs.filterByID(10500, 10599);
		for(int i=0;i<vs.getAllEntries().size();i++)
			System.out.println(vs.getAllEntries().get(i));
	}
}