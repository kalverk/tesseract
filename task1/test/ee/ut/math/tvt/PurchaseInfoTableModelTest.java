package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModelTest {
	PurchaseInfoTableModel model;
	PurchaseInfoTableModel model2;
	private SoldItem item;
	private SoldItem item2;
	private StockItem stockitem;
	private StockItem stockitem2;

	@Before
	public void setUp() {
		stockitem = new StockItem("asi", "kirjeldus", 99.9, 10);
		stockitem2 = new StockItem("asi2", "kirjeldus2", 999, 20);
		model = new PurchaseInfoTableModel();
		model2 = new PurchaseInfoTableModel();
		item = new SoldItem(stockitem, 1);
		model.addItem(item);
	}

	@Test
	public void testGetColumnValue() {
		assertEquals(item.getId(), model.getColumnValue(item, 0));
		assertEquals(item.getName(), model.getColumnValue(item, 1));
		assertEquals(item.getPrice(), model.getColumnValue(item, 2));
		assertEquals(item.getQuantity(), model.getColumnValue(item, 3));
		assertEquals(item.getSum(), model.getColumnValue(item, 4));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetColumnValueWhenThrowsException() {
		model.getColumnValue(item, 5);

	}
	
	@Test
	public void testGetStockItemWhenItemExsists() {
		assertNotNull(model.getStockItem(stockitem));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetStockItemWhenThrowsException() {
		model.getStockItem(stockitem2);
	}
	
	@Test
	public void testtotal_sumWithNoItems() {
		assertEquals(model2.total_sum(), "0.0");
	}
	
	@Test
	public void testtotal_sumWithOneItem() {
		assertEquals(model.total_sum(), String.valueOf(stockitem.getPrice()));
	}
	
	@Test
	public void testtotal_sumWithMultipleItems() {
		item2 = new SoldItem(stockitem2, 1);
		model.addItem(item2);
		assertEquals(model.total_sum(), String.valueOf(stockitem.getPrice() + stockitem2.getPrice()));
	}
	
	@Test
	public void testtotal_quantity() {
		assertEquals(model.total_quantity(stockitem), 1);
	}
	
	@Test
	public void testtotal_quantityWithNoItems() {
		assertEquals(model2.total_quantity(stockitem), 0);
	}
}