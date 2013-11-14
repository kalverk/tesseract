package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.AcceptOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab {

	private static final Logger log = Logger.getLogger(PurchaseTab.class);

	private final SalesDomainController domainController;

	private JButton newPurchase;

	private JButton submitPurchase;

	private JButton cancelPurchase;

	private JButton accept;

	private JButton cancel;

	private PurchaseItemPanel purchasePane;

	private SalesSystemModel model;

	private JDialog dialog;

	private JTextField textField;

	private JTextField textField1;

	private double sum;

	public PurchaseTab(SalesDomainController controller, SalesSystemModel model) {
		this.domainController = controller;
		this.model = model;
	}

	/**
	 * The purchase tab. Consists of the purchase menu, current purchase dialog
	 * and shopping cart table.
	 */
	public Component draw() {
		JPanel panel = new JPanel();

		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// Add the purchase menu
		panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

		// Add the main purchase-panel
		purchasePane = new PurchaseItemPanel(model);
		panel.add(purchasePane, getConstraintsForPurchasePanel());

		return panel;
	}

	// The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
	private Component getPurchaseMenuPane() {
		JPanel panel = new JPanel();

		// Initialize layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraintsForMenuButtons();

		// Initialize the buttons
		newPurchase = createNewPurchaseButton();
		submitPurchase = createConfirmButton();
		cancelPurchase = createCancelButton();

		// Add the buttons to the panel, using GridBagConstraints we defined
		// above
		panel.add(newPurchase, gc);
		panel.add(submitPurchase, gc);
		panel.add(cancelPurchase, gc);

		return panel;
	}

	// Creates the button "New purchase"
	private JButton createNewPurchaseButton() {
		JButton b = new JButton("New purchase");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPurchaseButtonClicked();
			}
		});

		return b;
	}

	// Creates the "Confirm" button
	private JButton createConfirmButton() {
		JButton b = new JButton("Confirm");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				additionalScreen();
			}
		});
		b.setEnabled(false);

		return b;
	}

	// Creates the "Cancel" button
	private JButton createCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	private JButton acceptPaymentButton() {
		JButton b = new JButton("Accept");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// tee midagi kui accepted
				submitPurchaseButtonClicked();
				dialog.dispose();
			}
		});
		b.setEnabled(false);
		return b;
	}

	private JButton cancelPaymentButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// tee midagi kui accepted
				cancelPurchaseButtonClicked();
				dialog.dispose();
			}
		});
		b.setEnabled(false);
		return b;
	}

	private JTextField displayInputField() {
		textField = new JTextField(15);
		textField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				try {
					if (textField.getText().length() > 0
							&& Double.parseDouble(textField.getText()) >= 0) {
						double input = Double.parseDouble(textField.getText());
						return_money(input);
					} else {
						textField1.setText("0.0");
					}
				} catch (NumberFormatException e) {
					// JOptionPane.showMessageDialog(purchasePane,"Invalid input. Please use numbers only!");
					log.error(e);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				try {
					if (textField.getText().length() > 0
							&& Double.parseDouble(textField.getText()) >= 0) {
						double input = Double.parseDouble(textField.getText());
						return_money(input);
					}
				} catch (NumberFormatException e) {
					textField1.setText("ERROR");
					JOptionPane.showMessageDialog(purchasePane,
							"Invalid input. Please use numbers only!");
					log.error(e);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				try {
					if (textField.getText().length() > 0
							&& Double.parseDouble(textField.getText()) >= 0) {
						double input = Double.parseDouble(textField.getText());
						return_money(input);
					}
				} catch (NumberFormatException e) {
					textField1.setText("ERROR");
					JOptionPane.showMessageDialog(purchasePane,
							"Invalid input. Please use numbers only!");
					log.error(e);
				}
			}
		});
		return textField;
	}

	/*
	 * === Event handlers for the menu buttons (get executed when the buttons
	 * are clicked)
	 */

	/** Event handler for the <code>new purchase</code> event. */
	protected void newPurchaseButtonClicked() {
		log.info("New sale process started");
		try {
			domainController.startNewPurchase();
			startNewSale();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPurchaseButtonClicked() {
		log.info("Sale cancelled");
		try {
			domainController.cancelCurrentPurchase();
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>submit purchase</code> event. */
	protected void submitPurchaseButtonClicked() {
		// Payment Accepted
		log.info("Sale complete");
		try {
			log.debug("Contents of the current basket:\n"
					+ model.getCurrentPurchaseTableModel());
			domainController.submitCurrentPurchase(model
					.getCurrentPurchaseTableModel().getTableRows());
			endSale();
			savePurchase();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	protected void savePurchase() {
		AcceptOrder order = new AcceptOrder(model
				.getCurrentPurchaseTableModel().getTableRows(),
				((DateFormat) new SimpleDateFormat("yyyy-MM-dd"))
						.format(Calendar.getInstance().getTime()),
				((DateFormat) new SimpleDateFormat("HH:mm:ss")).format(Calendar
						.getInstance().getTime()));
		model.getWarehouseTableModel().decreaseItemsQuantity(
				order.getSoldItems());
		model.getHistoryTableModel().addItem(order);
	}

	/*
	 * === Helper methods that bring the whole purchase-tab to a certain state
	 * when called.
	 */

	// switch UI to the state that allows to proceed with the purchase
	private void startNewSale() {
		purchasePane.reset();

		purchasePane.setEnabled(true);
		submitPurchase.setEnabled(true);
		cancelPurchase.setEnabled(true);
		newPurchase.setEnabled(false);
	}

	// switch UI to the state that allows to initiate new purchase
	private void endSale() {
		purchasePane.reset();

		cancelPurchase.setEnabled(false);
		submitPurchase.setEnabled(false);
		newPurchase.setEnabled(true);
		purchasePane.setEnabled(false);
	}

	private void saleOK() {
		purchasePane.reset();

		accept.setEnabled(true);
		cancel.setEnabled(true);
	}

	private void saleNotOK() {
		purchasePane.reset();

		accept.setEnabled(false);
		cancel.setEnabled(true);
	}

	/*
	 * === Next methods just create the layout constraints objects that control
	 * the the layout of different elements in the purchase tab. These
	 * definitions are brought out here to separate contents from layout, and
	 * keep the methods that actually create the components shorter and cleaner.
	 */

	private GridBagConstraints getConstraintsForPurchaseMenu() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		return gc;
	}

	private GridBagConstraints getConstraintsForPurchasePanel() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;

		return gc;
	}

	// The constraints that control the layout of the buttons in the purchase
	// menu
	private GridBagConstraints getConstraintsForMenuButtons() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridwidth = GridBagConstraints.RELATIVE;
		return gc;
	}

	public void additionalScreen() {
		JFrame frame = new JFrame();
		dialog = new JDialog(frame, "Confirm order");
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory
				.createTitledBorder("Confirmation required"));

		GridBagConstraints c = getConstraintsForPurchasePanel();
		JLabel total = new JLabel("Total sum is: ");
		getPurchaseSum();
		JLabel total_sum = new JLabel(String.valueOf(sum));
		panel.add(total);
		panel.add(total_sum, c);
		JLabel label = new JLabel("Payment amount: ");
		panel.add(label, c);
		textField = displayInputField();
		panel.add(textField, c);
		JLabel label1 = new JLabel("Change: ");
		panel.add(label1, c);
		textField1 = new JTextField(15);
		textField1.setEditable(false);
		panel.add(textField1, c);

		accept = acceptPaymentButton();
		GridBagConstraints gc = getConstraintsForMenuButtons();
		gc.insets = new Insets(10, 0, 0, 0);
		panel.add(accept, gc);
		cancel = cancelPaymentButton();
		panel.add(cancel, gc);
		saleNotOK();

		dialog.add(panel);
		dialog.pack();
		dialog.setLocation(purchasePane.getLocationOnScreen());
		dialog.setVisible(true);
	}

	private void return_money(double input) {
		double to_return = input - sum;
		if (to_return >= 0) {
			saleOK();
		} else {
			saleNotOK();
		}
		textField1
				.setText(String.valueOf(Math.round(to_return * 100.0) / 100.0));
	}

	private void getPurchaseSum() {
		sum = Double.parseDouble(model.getCurrentPurchaseTableModel()
				.total_sum());
	}
}
