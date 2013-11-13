package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;







import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.AcceptOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;
import ee.ut.math.tvt.salessystem.service.HibernateDataService;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

	private static final Logger log = Logger.getLogger(SalesSystemUI.class);
    private List<AcceptOrder> purchaseList;
    private List<StockItem> stockItems;
    HibernateDataService service = new HibernateDataService();
    
    
    public SalesDomainControllerImpl() {
            stockItems = new ArrayList<StockItem>();
            purchaseList = new ArrayList<AcceptOrder>();
            stockItems.addAll(service.getStockItems());
            purchaseList.addAll(service.getAcceptOrders());
            for (AcceptOrder p : purchaseList) {
                    float total = 0;
                    for (SoldItem s : p.getSoldItems()) {
                            total += s.getSum();
                    }
                    p.setTotal(total);
            }
    }

    public void submitCurrentPurchase(List<SoldItem> goods)
                    throws VerificationFailedException {
            for (SoldItem item : goods) {
                    for (StockItem stockitem : stockItems) {
                            if (stockitem.getId() == item.getId()) {
                                    stockitem.setQuantity(stockitem.getQuantity()
                                                    - item.getQuantity());
                            }
                    }
            }
            AcceptOrder purchase = new AcceptOrder(new Date(), goods);
            purchaseList.add(purchase);
            for (StockItem s: stockItems) {
                    service.update(s);
            }
            service.addAcceptOrder(purchase);
            for (SoldItem s : purchase.getSoldItems()) {
                    s.setAcceptorder(purchase);
                    service.addSoldItem(s);}
            }
	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		List<StockItem> dataset = service.getStockItems();
		/*
		 * StockItem chips = new StockItem(1l, "Lays chips", "Potato chips",
		 * 11.0, 5); StockItem chupaChups = new StockItem(2l, "Chupa-chups",
		 * "Sweets", 8.0, 8); StockItem frankfurters = new StockItem(3l,
		 * "Frankfurters", "Beer sauseges", 15.0, 12); StockItem beer = new
		 * StockItem(4l, "Free Beer", "Student's delight", 0.0, 100);
		 * 
		 * dataset.add(chips); dataset.add(chupaChups);
		 * dataset.add(frankfurters); dataset.add(beer);
		 */

		return dataset;
	}

	@Override
	public void endSession() {
		HibernateUtil.closeSession();

	}

	@Override
	public void addStockItem(StockItem stockitem) {
		service.addStockItem(stockitem);
		
	}

	@Override
	public List<AcceptOrder> loadHistoryState() {
		List<AcceptOrder> dataset = service.getAcceptOrders();
		return dataset;
	}
}
