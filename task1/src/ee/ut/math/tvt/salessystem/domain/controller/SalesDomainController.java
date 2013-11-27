package ee.ut.math.tvt.salessystem.domain.controller;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.AcceptOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;

public interface SalesDomainController {

	public List<StockItem> loadWarehouseState();

	public void startNewPurchase() throws VerificationFailedException;

	public void cancelCurrentPurchase() throws VerificationFailedException;

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException;

	public void endSession();

	public void addStockItem(StockItem stockitem);

	public List<AcceptOrder> loadHistoryState();
}
