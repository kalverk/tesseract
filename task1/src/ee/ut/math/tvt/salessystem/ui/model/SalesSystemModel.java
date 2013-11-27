package ee.ut.math.tvt.salessystem.ui.model;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.AcceptOrder;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {

	// Warehouse model
	private StockTableModel warehouseTableModel;

	// History tab model
	private HistoryTableModel historyTableModel;

	// Current shopping cart model
	private PurchaseInfoTableModel currentPurchaseTableModel;

	private final SalesDomainController domainController;

	/**
	 * Construct application model.
	 * 
	 * @param domainController
	 *            Sales domain controller.
	 */
	public SalesSystemModel(SalesDomainController domainController) {
		this.domainController = domainController;

		warehouseTableModel = new StockTableModel();
		historyTableModel = new HistoryTableModel();
		currentPurchaseTableModel = new PurchaseInfoTableModel();
		// populate stock model with data from the warehouse
		warehouseTableModel.populateWithData(domainController
				.loadWarehouseState());
		historyTableModel.populateWithData(domainController.loadHistoryState());

	}

	public StockTableModel getWarehouseTableModel() {
		return warehouseTableModel;
	}

	public HistoryTableModel getHistoryTableModel() {
		return historyTableModel;
	}

	public void setHistoryTabelModel(List<AcceptOrder> orders) {
		historyTableModel.populateWithData(orders);
	}

	public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
		return currentPurchaseTableModel;
	}
}
