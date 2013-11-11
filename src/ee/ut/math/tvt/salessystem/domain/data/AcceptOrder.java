package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ACCEPTORDER")
public class AcceptOrder implements DisplayableItem {

	private static long ID = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final Long id;

	@OneToMany(mappedBy = "sale_id")
	private final List<SoldItem> solditems;

	@Column(name = "DATE")
	private final String date;

	@Column(name = "TIME")
	private final String time;

	public AcceptOrder(List<SoldItem> solditems, String date, String time) {
		this.solditems = solditems;
		this.date = date;
		this.time = time;
		this.id = ID;
		ID += 1;
	}

	@Override
	public Long getId() {
		return id;
	}

	public List<SoldItem> getSoldItems() {
		return solditems;
	}

	public String getName() {
		return null;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public String getTotalSum() {
		Double purchaseSum = 0.0;
		for (final SoldItem item : solditems) {
			purchaseSum += item.getSum();
		}
		return purchaseSum.toString();
	}
}
