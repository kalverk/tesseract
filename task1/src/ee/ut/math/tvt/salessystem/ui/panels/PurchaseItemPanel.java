package ee.ut.math.tvt.salessystem.ui.panels;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Purchase pane + shopping cart table UI.
 */
public class PurchaseItemPanel extends JPanel {

	private static final Logger log = Logger.getLogger(PurchaseItemPanel.class);

	private static final long serialVersionUID = 1L;

	// Text field on the dialogPane
	private JTextField barCodeField;
	private JTextField quantityField;
	private JComboBox<String> nameField;
	private JTextField priceField;

	private JButton addItemButton;
	private PurchaseItemPanel purchasePane;

	// Warehouse model
	private SalesSystemModel model;

	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */
	public PurchaseItemPanel(SalesSystemModel model) {
		this.model = model;

		setLayout(new GridBagLayout());

		add(drawDialogPane(), getDialogPaneConstraints());
		add(drawBasketPane(), getBasketPaneConstraints());

		setEnabled(false);
	}

	// shopping cart pane
	private JComponent drawBasketPane() {

		// Create the basketPane
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		JTable table = new JTable(model.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		return basketPane;
	}

	// purchase dialog
	private JComponent drawDialogPane() {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// Initialize the textfields/comboBox
		barCodeField = new JTextField();
		quantityField = new JTextField("1");
		nameField = new JComboBox<String>(); // comboBox to select names.
		priceField = new JTextField();

		// Fill the dialog fields if action is performed.
		nameField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameField.getSelectedItem() != null) {
					Object selected = nameField.getSelectedItem();
					if (selected.toString().equals("")) {
						barCodeField.setText("");
						priceField.setText("");
					} else {
						fillDialogFields();
					}
				}
			}

		});
		barCodeField.setEditable(false);
		nameField.setEditable(false);
		priceField.setEditable(false);

		// - bar code
		panel.add(new JLabel("Bar code:"));
		panel.add(barCodeField);

		// - amount
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);

		// - name
		panel.add(new JLabel("Name:"));
		panel.add(nameField);

		// - price
		panel.add(new JLabel("Price:"));
		panel.add(priceField);

		// Create and add the button
		addItemButton = new JButton("Add to cart");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemEventHandler();
			}
		});

		panel.add(addItemButton);

		return panel;
	}

	public void addItemsToNameField() {
		// reseting nameField
		nameField.removeAllItems();
		// filling nameField with items
		nameField.addItem("");
		java.util.List<StockItem> items = model.getWarehouseTableModel()
				.getTableRows();
		for (StockItem item : items) {
			nameField.addItem(item.getName());
		}
	}

	// Fill dialog with data from the "database".
	public void fillDialogFields() {
		StockItem stockItem = getStockItemByName();

		if (stockItem != null) {
			barCodeField.setText(String.valueOf(stockItem.getId()));
			String priceString = String.valueOf(stockItem.getPrice());
			priceField.setText(priceString);
		}
	}

	// Search the warehouse for a StockItem with the selected item from
	// nameField
	private StockItem getStockItemByName() {
		try {
			String itemName = (String) nameField.getSelectedItem();
			if (itemName != null) {
				return model.getWarehouseTableModel().getItemByName(itemName);

			} else {
				return null;
			}
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	// Search the warehouse for a StockItem with the bar code entered
	// to the barCode textfield.
	private StockItem getStockItemByBarcode() {
		try {
			int code = Integer.parseInt(barCodeField.getText());
			return model.getWarehouseTableModel().getItemById(code);
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	/**
	 * Add new item to the cart.
	 */
	public void addItemEventHandler() {
		// add chosen item to the shopping cart.
		StockItem stockItem = getStockItemByBarcode();
		if (stockItem != null) {
			int quantity;
			try {
				quantity = Integer.parseInt(quantityField.getText());
				if (quantity <= 0) {
					throw new NumberFormatException();

				}
			} catch (NumberFormatException ex) {
				JOptionPane
						.showMessageDialog(purchasePane,
								"Invalid input on amount field. Please use positive numbers only!");
				log.error(ex);
				quantity = 1;
			}
			SoldItem exsistingItem = getExsistingItems(stockItem);

			try {
				if (stockItem.getQuantity()
						- model.getCurrentPurchaseTableModel().total_quantity(
								stockItem) < quantity) {

					throw new VerificationFailedException("not enough items");
				}

				if (exsistingItem != null) {
					quantity = exsistingItem.getQuantity() + quantity;
					model.getCurrentPurchaseTableModel().updateItem(
							exsistingItem, quantity);
				} else {
					model.getCurrentPurchaseTableModel().addItem(
							new SoldItem(stockItem, quantity));
				}

			} catch (VerificationFailedException e) {

				JFrame raam = new JFrame();
				raam.setSize(300, 300);
				raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JOptionPane.showMessageDialog(raam,
						"Warehouse doesn't have enough items!");
				log.error(e);
				raam.setVisible(false);
			}
		}
	}

	private SoldItem getExsistingItems(StockItem item) {
		try {
			return model.getCurrentPurchaseTableModel().getStockItem(item);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * Sets whether or not this component is enabled.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.addItemButton.setEnabled(enabled);
		this.barCodeField.setEnabled(enabled);
		this.quantityField.setEnabled(enabled);
		this.nameField.setEnabled(enabled);
	}

	/**
	 * Reset dialog fields.
	 */
	public void reset() {
		addItemsToNameField();
		barCodeField.setText("");
		priceField.setText("");
		quantityField.setText("1");

	}

	// Formatting constraints for the dialogPane
	private GridBagConstraints getDialogPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	// Formatting constraints for the basketPane
	private GridBagConstraints getBasketPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	private GridBagConstraints getBacketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

}
