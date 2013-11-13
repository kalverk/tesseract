package ee.ut.math.tvt.salessystem.domain.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import ee.ut.math.tvt.salessystem.service.HibernateDataService;

@Entity
@Table(name = "ACCEPTORDER")
public class AcceptOrder implements DisplayableItem {

	private static long ID = 1;
	//HibernateDataService service = new HibernateDataService();


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "acceptorder")
	private List<SoldItem> solditems;

	@Column(name = "DATE")
	private String date;

	@Column(name = "TIME")
	private String time;
	
	@Transient
	private float total;

	public AcceptOrder(List<SoldItem> solditems, String date, String time) {
		this.solditems = solditems;
		this.date = date;
		this.time = time;
		this.id = ID;
		ID += 1;
	}

	public AcceptOrder(Long id, String date, String time) {
		this.id = ID;
		this.date = date;
		this.time = time;
	}
	public AcceptOrder(Date date, List<SoldItem> soldItems) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        this.date = dateFormat.format(date);
        this.time = timeFormat.format(date);
        total = 0;
        for (SoldItem s : soldItems) {
                total += s.getSum();
        }
        System.out.println(total);
        this.solditems = solditems;
}
	public AcceptOrder() {
		
	}
	


	@Override
	public Long getId() {
		return id;
	}
	
	


	public List<SoldItem> getSoldItems() {
	//	solditems = service.getSoldItems();

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
	
	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getTotalSum() {
		Double purchaseSum = 0.0;
		for (final SoldItem item : solditems) {
			purchaseSum += item.getSum();
		}
		return purchaseSum.toString();
	}
}

