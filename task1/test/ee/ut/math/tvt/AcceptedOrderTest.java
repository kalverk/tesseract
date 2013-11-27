package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.AcceptOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class AcceptedOrderTest {
	private AcceptOrder order;

	@Before
	public void setUp() {
		order = new AcceptOrder(new ArrayList<SoldItem>(), "10-10-2013",
				"12:12:01");
	}

	@Test
	public void testAddSoldItem() {
		SoldItem item = new SoldItem(
				new StockItem("asi", "kirjeldus", 99.9, 10), 1);
		order.addSoldItem(item);
		assertEquals(order.getSoldItems().contains(item), true);

	}

	@Test
	public void testGetSumWithNoItems() {
		assertEquals(order.getTotalSum(), "0.0");
	}

	@Test
	public void testGetSumWithOneItem() {
		SoldItem item = new SoldItem(
				new StockItem("asi", "kirjeldus", 99.9, 10), 1);
		order.addSoldItem(item);
		assertEquals(order.getTotalSum(), String.valueOf(item.getPrice()));
	}

	@Test
	public void testGetSumWithMultipleItems() {
		SoldItem item2 = new SoldItem(new StockItem("Asi2", "2kirjeldus", 21.5,
				2), 2);
		SoldItem item3 = new SoldItem(new StockItem("Asi3", "3kirjeldus", 55.5,
				3), 3);
		SoldItem item = new SoldItem(
				new StockItem("asi", "kirjeldus", 10.5, 10), 1);

		order.addSoldItem(item3);
		order.addSoldItem(item2);
		order.addSoldItem(item);

		assertEquals(
				order.getTotalSum(),
				String.valueOf(item.getPrice() * 1 + item2.getPrice() * 2
						+ item3.getPrice() * 3));

	}
}
