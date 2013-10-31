package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.AcceptOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {

	private static final Logger log = Logger
			.getLogger(PurchaseInfoTableModel.class);
	private final SalesSystemModel model;

	public HistoryTab(SalesSystemModel model) {
		this.model = model;
	}

	// warehouse stock tab - consists of a menu and a table
	public Component draw() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		panel.setLayout(gb);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		panel.add(drawHistoryMenuPane(), gc);

		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawHistoryMainPane(), gc);
		return panel;
	}

	// history menu
	private Component drawHistoryMenuPane() {
		JPanel panel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);

		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;

		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return panel;
	}

	// table of history
	private Component drawHistoryMainPane() {
		final JPanel panel = new JPanel();

		final JTable table = new JTable(model.getHistoryTableModel());

		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					int row = table.getSelectedRow();
					AcceptOrder order = model.getHistoryTableModel().getOrder(
							row);
					JDialog dialog = populateDetailedInfoWindow(order);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		panel.setLayout(gb);
		panel.add(scrollPane, gc);
		panel.setBorder(BorderFactory.createTitledBorder("Accepted orders"));
		return panel;
	}

	private JDialog populateDetailedInfoWindow(AcceptOrder order) {
		JFrame frame = new JFrame();
		JDialog dialog = new JDialog(frame, "Order details");
		JPanel panel = new JPanel(new GridBagLayout());
		JTable itemInfo = populateTable(order);
		panel.add(new JScrollPane(itemInfo));
		panel.setBorder(BorderFactory
				.createTitledBorder("Details of order nr: " + order.getId()));
		panel.add(itemInfo.getTableHeader());
		dialog.add(panel);
		dialog.pack();
		dialog.setLocationRelativeTo(panel);
		dialog.setVisible(true);
		return dialog;
	}

	private JTable populateTable(AcceptOrder order) {
		List<SoldItem> soldItems = order.getSoldItems();

		Vector<String> columnNames = new Vector<String>();
		columnNames.addElement("Item name");
		columnNames.addElement("Amount");
		columnNames.addElement("Unit price");
		columnNames.addElement("Total sum");

		Vector<Vector> data = new Vector<Vector>();
		for (SoldItem item : soldItems) {
			Vector<String> row = new Vector<String>();
			row.addElement(item.getName());
			row.addElement(String.valueOf(item.getQuantity()));
			row.addElement(String.valueOf(item.getPrice()));
			row.addElement(String.valueOf(item.getSum()));
			data.addElement(row);
		}

		JTable table = new JTable(data, columnNames);

		return table;
	}

}