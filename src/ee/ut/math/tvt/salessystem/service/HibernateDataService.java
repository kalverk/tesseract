package ee.ut.math.tvt.salessystem.service;

import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.AcceptOrder;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class HibernateDataService {

	private Session session = HibernateUtil.currentSession();

	public List<StockItem> getStockItems() {
		List<StockItem> result = session.createQuery("from StockItem").list();
		return result;
	}

	public List<AcceptOrder> getAcceptOrders() {
		List<AcceptOrder> result = session.createQuery("from AcceptOrder")
				.list();
		return result;
	}

	public List<SoldItem> getSoldItems() {
		List<SoldItem> result = session.createQuery("from SoldItem").list();
		return result;
	}

	public void update(StockItem stockitem) {
		session.beginTransaction();
		session.update(stockitem);
		session.getTransaction().commit();
	}

	public void addStockItem(StockItem stockitem) {
		session.beginTransaction();
		session.save(stockitem);
		session.getTransaction().commit();
	}
	public void addSoldItem(SoldItem solditem) {
		session.beginTransaction();
		session.save(solditem);
		session.getTransaction().commit();
	}
	public void addAcceptOrder(AcceptOrder acceptorder) {
		session.beginTransaction();
		session.save(acceptorder);
		session.getTransaction().commit();
	}

}
