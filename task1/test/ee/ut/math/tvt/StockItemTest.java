package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockItemTest {
	private StockItem item;

	@Before
	public void setUp() {
		item = new StockItem("Mingitoode", "mingikirjeldus", 12.00, 99);
	}

	@Test
	public void testClone() {
		StockItem clone = (StockItem) item.clone();
		assertEquals(item.getId(), clone.getId());
		assertEquals(item.getName(), clone.getName());
		assertEquals(item.getDescription(), clone.getDescription());
		assertEquals(item.getPrice(), clone.getPrice(), 0.01);
		assertEquals(item.getQuantity(), clone.getQuantity());
	}

	@Test
	public void testGetColumn() {
		assertEquals(item.getId(), item.getColumn(0));
		assertEquals(item.getName(), item.getColumn(1));
		assertEquals(item.getPrice(), item.getColumn(2));
		assertEquals(item.getQuantity(), item.getColumn(3));
	}
}
