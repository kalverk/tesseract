package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;

public class AcceptOrder implements DisplayableItem {

	private static long ID = 1;

	private final Long id;

	private final List<SoldItem> solditems;

	private final String date;

	private final String time;


	public AcceptOrder(List<SoldItem> solditems, String date, String time
			) {
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
