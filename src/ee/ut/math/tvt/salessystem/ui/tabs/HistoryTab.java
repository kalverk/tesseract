package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Component;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {

	private static final Logger log = Logger.getLogger(PurchaseInfoTableModel.class);
	private final SalesSystemModel model;
	
	
	
	public HistoryTab(SalesSystemModel model) {
		this.model = model;
	}

	public Component draw() {
		JPanel panel = new JPanel();
		return panel;
	}
	
}