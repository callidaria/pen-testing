package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import basic.InventoryEntry;
import basic.Product;
import controller.VirtualStorage;

public class Validierungstests {
	@Test
	public void longTermSaveTest() {
		VirtualStorage vs = new VirtualStorage();
		List<InventoryEntry> xs = vs.getAllEntries();
		InventoryEntry x = new InventoryEntry(100,100,new Product("testentity",300,100,200,0));
		try { vs.addProduct(x.getUID(),x.getProduct().getName(),x.getProduct().getCount(),x.getProduct().getWeight(),
				x.getProduct().getPrize(),x.getProduct().getCategoryID()); } catch (Exception e) { e.printStackTrace(); }
		vs = new VirtualStorage();
		List<InventoryEntry> ys = vs.getAllEntries();
		xs.add(x);
		assertEquals(ys.size(),xs.size());
		for (int i = 0; i < ys.size(); i++) {
			assertEquals(ys.get(i).getUID(),xs.get(i).getUID());
			assertEquals(ys.get(i).getProduct().getName(),xs.get(i).getProduct().getName());
			assertEquals(ys.get(i).getProduct().getCount(),xs.get(i).getProduct().getCount());
			assertEquals(ys.get(i).getProduct().getWeight(),xs.get(i).getProduct().getWeight());
			assertEquals(ys.get(i).getProduct().getPrize(),xs.get(i).getProduct().getPrize());
			assertEquals(ys.get(i).getProduct().getCategoryID(),xs.get(i).getProduct().getCategoryID());
		} try { vs.deleteProduct(100100); } catch (Exception e) { e.printStackTrace(); }
	}
	
	@Test
	public void uniquenessTest() {
		int res = 0;
		VirtualStorage vs = new VirtualStorage();
		try {
			vs.addProduct(100100, "testentity", 300, 100, 200, 0);
			res = vs.addProduct(100100, "new entity", 300, 100, 200, 0);
			assertEquals(-2,res);
			res = vs.addProduct(100101, "testentity", 300, 100, 200, 0);
			assertEquals(-1,res);
			res = vs.addProduct(100101, "new entity", 300, 100, 200, 0);
			assertEquals(0,res);
			vs.deleteProduct(100100);
			vs.deleteProduct(100101);
			
			vs.addCategory("testcategory");
			res = vs.addCategory("testcategory");
			assertEquals(-1,res);
			res = vs.addCategory("new category");
			assertEquals(0,res);
			vs.removeCategory(1);
			vs.removeCategory(2);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	@Test
	public void searchTest() {
		VirtualStorage vs = new VirtualStorage();
		vs.addCategory("Schreibgeräte");
		vs.addCategory("Korrekturflüssigkeit");
		vs.addCategory("Büromaterial");
		vs.addCategory("Notizzettel");
		vs.addCategory("Hefter");
		vs.addCategory("Papier");
		vs.addCategory("Register");
		try {
			vs.addProduct(12, "Stift_blau_0.05mm", 360, 10, 60, 1);
			vs.addProduct(13, "Stift_schwarz_0.03mm", 130, 10, 60, 1);
			vs.addProduct(951167, "Tippex 3x", 97, 16, 495, 2);
			vs.addProduct(461168, "Tippex 10x", 48, 55, 1550, 2);
			vs.addProduct(332471, "Locher 25 Blatt rot", 37, 24, 625, 3);
			vs.addProduct(123483, "Post-it Block weiß", 460, 30, 215, 4);
			vs.addProduct(513513, "Hefter 100x bunt", 179, 450, 2500, 5);
			vs.addProduct(986722, "Textmarker 3x neongrün", 255, 2000, 350, 1);
			vs.addProduct(2001, "Kopierpapier 10x 500", 513, 2500, 3595, 6);
			vs.addProduct(501993, "Trennstreifen 5x 100 bunt", 83, 2, 2500, 7);
			
			vs.searchEntries("Tippex");
			List<InventoryEntry> xs = vs.getAllEntries();
			assertEquals(951167,xs.get(0).getUID());
			assertEquals(461168,xs.get(1).getUID());
			vs.loadVirtualStorage();
			vs.searchEntries("Locher 25 Blatt rot");
			xs = vs.getAllEntries();
			assertEquals(332471,xs.get(0).getUID());
			vs.loadVirtualStorage();
			vs.searchEntries("Kopierpapier 10x 500");
			xs = vs.getAllEntries();
			assertEquals(2001,xs.get(0).getUID());
			vs.loadVirtualStorage();
			vs.searchEntries("Tuppex 10x");
			xs = vs.getAllEntries();
			assertEquals(0,xs.size());
			vs.loadVirtualStorage();
			vs.searchEntries("3");
			xs = vs.getAllEntries();
			assertEquals(12,xs.get(0).getUID());
			assertEquals(13,xs.get(1).getUID());
			assertEquals(951167,xs.get(2).getUID());
			assertEquals(332471,xs.get(3).getUID());
			assertEquals(123483,xs.get(4).getUID());
			assertEquals(513513,xs.get(5).getUID());
			assertEquals(986722,xs.get(6).getUID());
			assertEquals(2001,xs.get(7).getUID());
			assertEquals(501993,xs.get(8).getUID());
			
			vs.deleteProduct(12);
			vs.deleteProduct(13);
			vs.deleteProduct(951167);
			vs.deleteProduct(461168);
			vs.deleteProduct(332471);
			vs.deleteProduct(123483);
			vs.deleteProduct(513513);
			vs.deleteProduct(986722);
			vs.deleteProduct(2001);
			vs.deleteProduct(501993);
		} catch (Exception e) { e.printStackTrace(); }
		for (int i = 100; i > 0; i--) { vs.removeCategory(i); }
	}
}