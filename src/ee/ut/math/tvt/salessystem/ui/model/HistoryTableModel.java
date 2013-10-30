package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;
import ee.ut.math.tvt.salessystem.domain.data.AcceptOrder;

/**
 * History item table model.
 */
public class HistoryTableModel extends SalesSystemTableModel<AcceptOrder> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);

	public HistoryTableModel() {
		super(new String[] { "Id", "Date", "Time", "Total" });
	}

	public void addItem(final AcceptOrder order) {

		rows.add(order);
		fireTableDataChanged();

	}

	@Override
	protected Object getColumnValue(AcceptOrder item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getDate();
		case 2:
			return item.getTime();
		case 3:
			return item.getTotalSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final AcceptOrder item : rows) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getDate() + "\t");
			buffer.append(item.getTime() + "\t");
			buffer.append(item.getTotalSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

}
