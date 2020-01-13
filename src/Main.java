import controller.VirtualStorage;

public class Main {
	public static void main(String[] args) throws Exception {
		VirtualStorage vs = new VirtualStorage();
		for (int i = 0; i < vs.getAllEntries().size(); i++)
			System.out.println(vs.getAllEntries().get(i));
		
		for (int i = 0; i < vs.getAllEntries().size(); i++)
			System.out.println(vs.getAllEntries().get(i));
	}
}