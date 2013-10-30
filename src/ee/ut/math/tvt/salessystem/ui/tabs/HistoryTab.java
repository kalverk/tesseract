package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

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

	// AcceptOrder variables
	private long id;
	private String date;
	private String time;
	private double total;

	// JTextFields for item info
	private JTextField idField;
	private JTextField dateField;
	private JTextField timeField;
	private JTextField totalField;

	// JTextLabels for item info

	private JLabel idLabel;
	private JLabel dateLabel;
	private JLabel timeLabel;
	private JLabel totalLabel;

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

	// warehouse menu
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

	// table of the wareshouse stock
	private Component drawHistoryMainPane() {
		JPanel panel = new JPanel();

		JTable table = new JTable(model.getHistoryTableModel());

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
}