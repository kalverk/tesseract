package ee.ut.math.tvt.salessystem.ui.model;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(PurchaseInfoTableModel.class);

	public PurchaseInfoTableModel() {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum" });
	}

	@Override
	public Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return item.getSum();

		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem item : rows) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	/**
	 * Add new StockItem to table.
	 */
	public void addItem(final SoldItem item) {
		/**
		 * XXX In case such stockItem already exists increase the quantity of
		 * the existing stock.
		 */

		rows.add(item);
		log.debug("Added " + item.getName() + " quantity of "
				+ item.getQuantity());
		fireTableDataChanged();
	}

	public void updateItem(SoldItem item, int quantity) {
		item.setQuantity(quantity);
		fireTableDataChanged();
	}

	public SoldItem getStockItem(StockItem item) {
		for (SoldItem i : rows) {
			if (i.getName().equalsIgnoreCase(item.getName())) {
				return i;
			}
		}
		throw new NoSuchElementException();
	}

	public String total_sum() {
		final StringBuffer buffer = new StringBuffer();
		double sum = 0;
		for (final SoldItem item : rows) {
			sum += item.getSum();
		}
		buffer.append(sum);
		return buffer.toString();
	}

	public int total_quantity(StockItem item2) {
		int quantity_sum = 0;
		for (final SoldItem item : rows) {
			if (item.getStockItem().getId() == item2.getId()) {
				quantity_sum += item.getQuantity();
			}
		}
		return quantity_sum;
	}
}