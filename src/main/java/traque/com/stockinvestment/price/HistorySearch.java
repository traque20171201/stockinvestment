package traque.com.stockinvestment.price;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class HistorySearch {

	private Integer exchange;
	
	private String stock;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_from;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_to;
	
	private Integer page;
	
	private Integer size;

	public Integer getExchange() {
		return exchange;
	}

	public void setExchange(Integer exchange) {
		this.exchange = exchange;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
