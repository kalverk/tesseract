package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.table.AbstractTableModel;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;

/**
 * Generic table model implementation suitable for extending.
 */
public abstract class SalesSystemTableModel<T extends DisplayableItem> extends
		AbstractTableModel {

	private static final long serialVersionUID = 1L;

	protected List<T> rows;
	protected final String[] headers;

	public SalesSystemTableModel(final String[] headers) {
		this.headers = headers;
		rows = new ArrayList<T>();
	}

	protected abstract Object getColumnValue(T item, int columnIndex);

	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		return headers[columnIndex];
	}

	public int getRowCount() {
		return rows.size();
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return getColumnValue(rows.get(rowIndex), columnIndex);
	}

	public T getItemById(final long id) {
		for (final T item : rows) {
			if (item.getId() == id)
				return item;
		}
		throw new NoSuchElementException();
	}

	public T getItemByName(final String name) {
		for (final T item : rows) {
			if (item.getName() == name)
				return item;
		}
		throw new NoSuchElementException();
	}

	public List<T> getTableRows() {
		return rows;
	}

	public void clear() {
		rows = new ArrayList<T>();
		fireTableDataChanged();
	}

	public void populateWithData(final List<T> data) {
		rows.clear();
		rows.addAll(data);
	}

}