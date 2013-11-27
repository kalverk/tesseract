package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

public class StockTableModelTest {

	StockTableModel model;
	StockItem item;

	@Before
	public void setUp() {
		model = new StockTableModel();
		item = new StockItem((long) 19, "Asi", "kirjeldus", 99.9, 12);
		model.addItem(item);
	}

	@Test
	public void testValidateNameUniqueness() {
		StockItem item2 = new StockItem((long) 19, "Asi", "kirjeldus", 19.9,
				132);
		model.addItem(item2);
		assertEquals(2, model.getTableRows().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasEnoughInStock() {
		SoldItem item1 = new SoldItem(item, 50);

	}

	@Test
	public void testGetItemByIdWhenItemExsists() {
		assertNotNull(model.getItemById(item.getId()));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() {
		int randomId = 0;
		model.getItemById(randomId);
	}
}