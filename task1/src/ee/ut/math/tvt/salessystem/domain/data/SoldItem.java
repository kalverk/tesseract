package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "SALE_ID", nullable = true)
	private AcceptOrder acceptorder;

	@ManyToOne
	@JoinColumn(name = "STOCKITEM_ID", nullable = false)
	private StockItem stockItem;

	@Column(name = "NAME")
	private String name;

	@Column(name = "QUANTITY")
	private Integer quantity;

	@Column(name = "ITEMPRICE")
	private double price;

	public SoldItem(StockItem stockItem, int quantity) {
		this.stockItem = stockItem;
		this.name = stockItem.getName();
		this.price = stockItem.getPrice();
		this.quantity = quantity;
		if (stockItem.getQuantity() < quantity) {

			throw new IllegalArgumentException("not enough items");
		}

	}

	public AcceptOrder getAcceptorder() {
		return acceptorder;
	}

	public void setAcceptorder(AcceptOrder acceptorder) {
		this.acceptorder = acceptorder;
	}

	public SoldItem() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getSum() {
		double sum = price * ((double) quantity);
		return Math.round(sum * 100) / 100.0;
	}

	public StockItem getStockItem() {
		return stockItem;
	}

	public void setStockItem(StockItem stockItem) {
		this.stockItem = stockItem;
	}

}
