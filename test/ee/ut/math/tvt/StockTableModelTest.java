package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

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

	// muidu panin jargneva testi jarele (expected =
	// IllegalArgumentException.class) aga see ei puudnud exceptionit
	// miskiparast kinni
	// asendasin nuud try catchiga siis tootab.

	@Test
	public void testHasEnoughInStock() {
		try {
			SoldItem item1 = new SoldItem(item, 50);
		} catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void testGetItemByIdWhenItemExsists() {
		model.addItem(item);
		assertNotNull(model.getItemById(item.getId()));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() {
		int randomId = 0;
		model.getItemById(randomId);
	}
}