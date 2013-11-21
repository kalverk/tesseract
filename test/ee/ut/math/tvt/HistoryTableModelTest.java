package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.AcceptOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTableModel;

public class HistoryTableModelTest {
	HistoryTableModel model;
	private AcceptOrder order;

	@Before
	public void setUp() {
		model = new HistoryTableModel();
		order = new AcceptOrder(new ArrayList<SoldItem>(), "12-10-2013",
				"12:12:08");
		SoldItem item = new SoldItem(
				new StockItem("asi", "kirjeldus", 99.9, 10), 1);
		order.addSoldItem(item);
		model.addItem(order);
	}

	@Test
	public void testGetColumn() {
		assertEquals(order.getId(), model.getColumnValue(order, 0));
		assertEquals(order.getDate(), model.getColumnValue(order, 1));
		assertEquals(order.getTime(), model.getColumnValue(order, 2));
		assertEquals(order.getTotalSum(), model.getColumnValue(order, 3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetColumnWhenThrowsException() {
		model.getColumnValue(order, 10);

	}
}
