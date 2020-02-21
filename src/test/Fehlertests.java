package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import basic.InventoryEntry;
import controller.VirtualStorage;

public class Fehlertests {
	VirtualStorage vs = new VirtualStorage();
	
	@Test
	public void blackboxPartitioningUIDTestLessZero() {
		clearTestDatabase();
		try {
			vs.addProduct(-1, "testentity", 200, 10, 400, 0);
			assertEquals(0,vs.getAllEntries().size());
		} catch (Exception e) { e.printStackTrace(); }
	}
	@Test
	public void blackboxPartitioningUIDTestValid() {
		clearTestDatabase();
		try {
			vs.addProduct(100100, "testentity", 200, 10, 400, 0);
			assertEquals(1,vs.getAllEntries().size());
		} catch (Exception e) { e.printStackTrace(); }
	}
	@Test
	public void blackboxPartitioningUIDTestOverLimit() {
		clearTestDatabase();
		try {
			vs.addProduct(1000000,"testentity", 200, 10, 400, 0);
			assertEquals(0,vs.getAllEntries().size());
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * Dieser Test schlägt fehl.
	 */
	@Test
	public void blackboxPartitioningNameTestLessOne() {
		clearTestDatabase();
		try {
			vs.addProduct(100100, "", 200, 10, 400, 0);
			assertEquals(0,vs.getAllEntries().size());
		} catch (Exception e) { e.printStackTrace(); }
	}
	@Test
	public void blackboxPartitioningNameTestValid() {
		clearTestDatabase();
		try {
			vs.addProduct(100100, "testentity", 200, 10, 400, 0);
			assertEquals(1,vs.getAllEntries().size());
		} catch (Exception e) { e.printStackTrace(); }
	}
	@Test
	public void blackboxPartitioningNameTestOverCharLimit() {
		clearTestDatabase();
		try {
			vs.addProduct(100100, "llllllllllllllllllllllllllllllllllllllllllllllllllllllll"
					+ "llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll"
					+ "llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll",
					200, 10, 400, 0);
			assertEquals(0,vs.getAllEntries().size());
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * Dieser Test schlägt fehl.
	 */
	@Test
	public void blackboxPartitioningCategoryTestLessOne() {
		clearTestDatabase();
		vs.addCategory("");
		assertEquals(1,vs.getAllCategories().size());
	}
	@Test
	public void blackboxPartitioningCategoryTestValid() {
		clearTestDatabase();
		vs.addCategory("testcategory");
		assertEquals(2,vs.getAllCategories().size());
	}
	@Test
	public void blackboxPartitioningCategoryTestOverCharLimit() {
		clearTestDatabase();
		vs.addCategory("llllllllllllllllllllllllllllllllllllllllllllllllllllllll"
					+ "llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll"
					+ "llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
		assertEquals(1,vs.getAllCategories().size());
	}
	
	@Test
	public void blackboxPartitioningWeightTestLessZero() {
		clearTestDatabase();
		try {
			vs.addProduct(100100, "testentity", 200, -1, 400, 0);
			assertEquals(0,vs.getAllEntries().size());
		} catch (Exception e) { e.printStackTrace(); }
	}
	@Test
	public void blackboxPartitioningWeightTestOverWeightLimit() {
		clearTestDatabase();
		try {
			vs.addProduct(100100, "testentity", 200, 1000001, 400, 0);
			assertEquals(0,vs.getAllEntries().size());
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	@Test
	public void blackboxPartitioningAmountLessZero() {
		clearTestDatabase();
		try {
			vs.addProduct(100100, "testentity", -1, 10, 400, 0);
			assertEquals(0,vs.getAllEntries().size());
		} catch (Exception e) { e.printStackTrace(); }
	}
	@Test
	public void blackboxPartitioningPrizeLessZero() {
		clearTestDatabase();
		try {
			vs.addProduct(100100, "testentity", 200, 10, -1, 0);
			assertEquals(0,vs.getAllEntries().size());
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private void clearTestDatabase() {
		List<InventoryEntry> xs = vs.getAllEntries();
		try {
			for (InventoryEntry x : xs) vs.deleteProduct(x.getUID());
			for (int i = 100; i > 0; i--) vs.removeCategory(i);
		} catch (Exception e) { e.printStackTrace(); }
	}
}