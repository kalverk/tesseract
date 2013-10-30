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
				if(SwingUtilities.isLeftMouseButton(e)){
					int row = table.getSelectedRow();
					AcceptOrder order = model.getHistoryTableModel().getOrder(row);
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
	
	private JDialog populateDetailedInfoWindow(AcceptOrder order){
		JDialog dialog = new JDialog();
		JPanel panel = new JPanel(new GridBagLayout());
		JTable itemInfo = populateTable(order);
		panel.setBorder(BorderFactory.createTitledBorder("Detailes of order nr: " + order.getId()));
		panel.add(itemInfo);
		dialog.add(panel);
		dialog.pack();
		dialog.setLocationRelativeTo(panel);
		dialog.setVisible(true);
		return dialog;
	}
	
	private JTable populateTable(AcceptOrder order){
		List<SoldItem> soldItems = order.getSoldItems();
		
		String[] columnNames = {"Item name", "Amount", "Price per piece", "Total sum"};
		Vector data = new Vector();
		for (SoldItem item:soldItems){
			String itemName = item.getName() + "\n";
			String itemAmount = item.getQuantity() + "\n";
			String itemPPP = item.getPrice() + "\n";
			String itemTotalSum = item.getSum() + "\n";
			data.add(new Vector(Arrays.asList(itemName)));
			data.add(new Vector(Arrays.asList(itemAmount)));
			data.add(new Vector(Arrays.asList(itemPPP)));
			data.add(new Vector(Arrays.asList(itemTotalSum)));
		}
		
		Vector columnName = new Vector<>(Arrays.asList(columnNames));
		
		JTable table = new JTable (data, columnName);
		
		return table;
	}

}