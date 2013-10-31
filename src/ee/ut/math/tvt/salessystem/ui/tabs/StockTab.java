package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

public class StockTab {

	private static final Logger log = Logger.getLogger(StockTab.class);

	private JButton addItem;

	private SalesSystemModel model;

	// StockItem variables
	private long id;
	private String name;
	private String desc;
	private double price;
	private int amount;

	String warningInfo = "";

	// JTextFields for item info
	private JTextField idField;
	private JTextField nameField;
	private JTextField descField;
	private JTextField priceField;
	private JTextField amountField;

	// JTextLabels for item info

	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel descLabel;
	private JLabel priceLabel;
	private JLabel amountLabel;

	public StockTab(SalesSystemModel model) {
		this.model = model;
	}

	// addItem button ->adding functionality

	private JButton createAddItemButton() {
		JButton b = new JButton("Add");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddItemButtonClicked();
			}
		});

		return b;
	}

	protected void AddItemButtonClicked() {
		log.info("Adding new item.");
		AddItem();
	}

	private void AddItem() {

		if (createItemInfoOption()) { // if item info editing tab isn't canceled
										// items fill be added.
			StockItem stockItem = new StockItem(id, name, desc, price, amount);
			model.getWarehouseTableModel().addItem(stockItem);
		} else {
			return;
		}

	}

	private boolean createItemInfoOption() {
		
		JFrame frame = new JFrame();
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(5, 2, 0, 5));

		idLabel = new JLabel("Bar code:");
		idField = new JTextField();
		idField.setName("Bar Code Field");
		nameLabel = new JLabel("Name:");
		nameField = new JTextField();
		nameField.setName("Name Field");
		descLabel = new JLabel("Description:");
		descField = new JTextField();
		descField.setName("Description Field");
		priceLabel = new JLabel("Price:");
		priceField = new JTextField();
		priceField.setName("Price Field");
		amountLabel = new JLabel("Quantity:");
		amountField = new JTextField();
		amountField.setName("Quantity Field");
		idField.setToolTipText("Add item Bar code here.");
		nameField.setToolTipText("Add item Name here.");
		descField.setToolTipText("Add item Description here.");
		priceField.setToolTipText("Add item Price here.");
		amountField.setToolTipText("Add item Amount here.");

		panel.add(idLabel);
		panel.add(idField);
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(descLabel);
		panel.add(descField);
		panel.add(priceLabel);
		panel.add(priceField);
		panel.add(amountLabel);
		panel.add(amountField);

		while (true) {
			int result = JOptionPane.showOptionDialog(frame, panel,
					"Item information adding", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (result == 0) {
				
				if (checkBlank(idField, true) && checkBlank(nameField, false)
						&& checkBlank(descField, false)
						&& checkBlank(priceField, true)
						&& checkBlank(amountField, true)
						&& isThisIdAvailable()) {// add values to item
												// info variables
					id = (long) (Double.parseDouble(idField.getText()));
					// did it that way because in checkBlank() is only checked
					// if Number is double. So if You write Double in Bar code
					// field program would automatically convert it into Long.
					name = nameField.getText();
					desc = descField.getText();
					price = Double.parseDouble(priceField.getText());
					amount = (int) (Double.parseDouble(amountField.getText()));
					//To add ID has to be available or price,name and id should match
					return true;
				} else {
					createWarning(warningInfo);
				}

			} else {
				return false;
			}
		}

	}
	
	private boolean isThisIdAvailable(){
		try{
			StockItem item = model.getWarehouseTableModel().getItemById(Long.parseLong(idField.getText()));
			if(Long.parseLong(idField.getText())==item.getId()&&
					nameField.getText().equalsIgnoreCase(item.getName())&&
					Double.parseDouble(priceField.getText())==item.getPrice()){
				return true;
			}
			else{
				warningInfo="This Id is occupied with another product. Try different Id.";
				return false;
			}
		}catch(NoSuchElementException e){
			//Means there is no such ID in warehouse we can occupy this ID.
			log.debug(e);
			return true;
		}
	}

	private boolean checkBlank(JTextField blank, boolean ifNumber) {// checks if
																	// filled
																	// line
																	// meets
																	// criterium
		String info = blank.getText();
		if (ifNumber && !info.equals("")) {
			try {
				Double x = Double.parseDouble(info);
				if (x < 0) {// check if number is bigger than 0, because bar
							// code, price and amount shouldn't be negative
					throw new NumberFormatException();
				}

				return true;
			} catch (NumberFormatException e) {
				String message = blank.getName()
						+ " should contain only positive digits.";
				log.info(message);
				warningInfo = message;
				return false;
			}

		} else if (info.equals("")) {
			String message = blank.getName() + " should be filled.";
			log.info(message);
			warningInfo = message;
			return false;
		} else

			return true;
	}

	private void createWarning(String message) {
		JFrame raam = new JFrame();
		raam.setSize(300, 300);
		raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JOptionPane.showMessageDialog(raam, message);
		raam.setVisible(false);
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

		panel.add(drawStockMenuPane(), gc);

		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawStockMainPane(), gc);
		return panel;
	}

	// warehouse menu
	private Component drawStockMenuPane() {
		JPanel panel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);

		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;

		addItem = createAddItemButton();// add addItem button

		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;
		panel.add(addItem, gc);

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return panel;
	}

	// table of the wareshouse stock
	private Component drawStockMainPane() {
		JPanel panel = new JPanel();

		JTable table = new JTable(model.getWarehouseTableModel());

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

		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
		return panel;
	}

}
