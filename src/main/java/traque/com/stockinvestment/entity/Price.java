package traque.com.stockinvestment.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Prices")
public class Price {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private Integer exchange;
    
    private Date date;
    
    private String stock;
    
    private Float refer;
    
    private Float ceiling;
    
    private Float floor;
    
    private Float open;
    
    private Float close;
    
    private Float highest;
    
    private Float lowest;
    
    private Float avge;
    
    private Float change_value;
    
    private Float change_percent;
    
    private Double volume_order;
    
    private Double volume_put;
    
    private Double volume_total;
    
    private Double value_order;
    
    private Double value_put;
    
    private Double value_total;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExchange() {
		return exchange;
	}

	public void setExchange(Integer exchange) {
		this.exchange = exchange;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public Float getRefer() {
		return refer;
	}

	public void setRefer(Float refer) {
		this.refer = refer;
	}

	public Float getCeiling() {
		return ceiling;
	}

	public void setCeiling(Float ceiling) {
		this.ceiling = ceiling;
	}

	public Float getFloor() {
		return floor;
	}

	public void setFloor(Float floor) {
		this.floor = floor;
	}

	public Float getOpen() {
		return open;
	}

	public void setOpen(Float open) {
		this.open = open;
	}

	public Float getClose() {
		return close;
	}

	public void setClose(Float close) {
		this.close = close;
	}

	public Float getHighest() {
		return highest;
	}

	public void setHighest(Float highest) {
		this.highest = highest;
	}

	public Float getLowest() {
		return lowest;
	}

	public void setLowest(Float lowest) {
		this.lowest = lowest;
	}

	public Float getAvge() {
		return avge;
	}

	public void setAvge(Float avge) {
		this.avge = avge;
	}

	public Float getChange_value() {
		return change_value;
	}

	public void setChange_value(Float change_value) {
		this.change_value = change_value;
	}

	public Float getChange_percent() {
		return change_percent;
	}

	public void setChange_percent(Float change_percent) {
		this.change_percent = change_percent;
	}

	public Double getVolume_order() {
		return volume_order;
	}

	public void setVolume_order(Double volume_order) {
		this.volume_order = volume_order;
	}

	public Double getVolume_put() {
		return volume_put;
	}

	public void setVolume_put(Double volume_put) {
		this.volume_put = volume_put;
	}

	public Double getVolume_total() {
		return volume_total;
	}

	public void setVolume_total(Double volume_total) {
		this.volume_total = volume_total;
	}

	public Double getValue_order() {
		return value_order;
	}

	public void setValue_order(Double value_order) {
		this.value_order = value_order;
	}

	public Double getValue_put() {
		return value_put;
	}

	public void setValue_put(Double value_put) {
		this.value_put = value_put;
	}

	public Double getValue_total() {
		return value_total;
	}

	public void setValue_total(Double value_total) {
		this.value_total = value_total;
	}
	
	public String getClassOpen() {
		if (Float.compare(this.open,this.refer) == 0) {
			return "reference";
		}
		if (Float.compare(this.open,this.ceiling) == 0) {
			return "ceiling";
		}
		if (Float.compare(this.open,this.floor) == 0) {
			return "floor";
		}
		if (Float.compare(this.open,this.floor) > 0 && Float.compare(this.open,this.refer) < 0) {
			return "reduce";
		}
		if (Float.compare(this.open,this.refer) > 0 && Float.compare(this.open,this.ceiling) < 0) {
			return "up";
		}
		return "";
	}
	
	public String getClassClose() {
		if (Float.compare(this.close,this.refer) == 0) {
			return "reference";
		}
		if (Float.compare(this.close,this.ceiling) == 0) {
			return "ceiling";
		}
		if (Float.compare(this.close,this.floor) == 0) {
			return "floor";
		}
		if (Float.compare(this.close,this.floor) > 0 && Float.compare(this.close,this.refer) < 0) {
			return "reduce";
		}
		if (Float.compare(this.close,this.refer) > 0 && Float.compare(this.close,this.ceiling) < 0) {
			return "up";
		}
		return "";
	}
	
	public String getClassHighest() {
		if (Float.compare(this.highest,this.refer) == 0) {
			return "reference";
		}
		if (Float.compare(this.highest,this.ceiling) == 0) {
			return "ceiling";
		}
		if (Float.compare(this.highest,this.floor) == 0) {
			return "floor";
		}
		if (Float.compare(this.highest,this.floor) > 0 && Float.compare(this.highest,this.refer) < 0) {
			return "reduce";
		}
		if (Float.compare(this.highest,this.refer) > 0 && Float.compare(this.highest,this.ceiling) < 0) {
			return "up";
		}
		return "";
	}
	
	public String getClassLowest() {
		if (Float.compare(this.lowest,this.refer) == 0) {
			return "reference";
		}
		if (Float.compare(this.lowest,this.ceiling) == 0) {
			return "ceiling";
		}
		if (Float.compare(this.lowest,this.floor) == 0) {
			return "floor";
		}
		if (Float.compare(this.lowest,this.floor) > 0 && Float.compare(this.lowest,this.refer) < 0) {
			return "reduce";
		}
		if (Float.compare(this.lowest,this.refer) > 0 && Float.compare(this.lowest,this.ceiling) < 0) {
			return "up";
		}
		return "";
	}
	
	public String getClassAvg() {
		if (Float.compare(this.avge,this.refer) == 0) {
			return "reference";
		}
		if (Float.compare(this.avge,this.ceiling) == 0) {
			return "ceiling";
		}
		if (Float.compare(this.avge,this.floor) == 0) {
			return "floor";
		}
		if (Float.compare(this.avge,this.floor) > 0 && Float.compare(this.avge,this.refer) < 0) {
			return "reduce";
		}
		if (Float.compare(this.avge,this.refer) > 0 && Float.compare(this.avge,this.ceiling) < 0) {
			return "up";
		}
		return "";
	}
}
