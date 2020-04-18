package traque.com.stockinvestment.entity;

import java.util.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(
		name = "PriceFluctuateResult",
		classes = {
				@ConstructorResult(
						targetClass = PriceFluctuate.class,
						columns = {
								@ColumnResult(name="t_stock"),
								@ColumnResult(name="t_close", type=Float.class),
								@ColumnResult(name="t_refer", type=Float.class),
								@ColumnResult(name="t_ceiling", type=Float.class),
								@ColumnResult(name="t_floor", type=Float.class),
								@ColumnResult(name="f_close", type=Float.class),
								@ColumnResult(name="f_refer", type=Float.class),
								@ColumnResult(name="f_ceiling", type=Float.class),
								@ColumnResult(name="f_floor", type=Float.class),
								@ColumnResult(name="c_volume", type=Float.class),
								@ColumnResult(name="c_percent", type=Float.class),
								// 20200418 ThinhNH Add Start - column min_compare_close
								@ColumnResult(name="b_volume", type=Float.class),
								@ColumnResult(name="b_percent", type=Float.class),
								// 20200418 ThinhNH Add End
								@ColumnResult(name="v_order", type=Double.class),
								@ColumnResult(name="v_total", type=Double.class),
								@ColumnResult(name="a_date", type=Date.class),
								@ColumnResult(name="a_close", type=Float.class),
								@ColumnResult(name="a_refer", type=Float.class),
								@ColumnResult(name="a_ceiling", type=Float.class),
								@ColumnResult(name="a_floor", type=Float.class),
								@ColumnResult(name="b_date", type=Date.class),
								@ColumnResult(name="b_close", type=Float.class),
								@ColumnResult(name="b_refer", type=Float.class),
								@ColumnResult(name="b_ceiling", type=Float.class),
								@ColumnResult(name="b_floor", type=Float.class),
								@ColumnResult(name="h_date", type=Date.class),
								@ColumnResult(name="h_highest", type=Float.class),
								@ColumnResult(name="h_refer", type=Float.class),
								@ColumnResult(name="h_ceiling", type=Float.class),
								@ColumnResult(name="h_floor", type=Float.class),
								@ColumnResult(name="l_date", type=Date.class),
								@ColumnResult(name="l_lowest", type=Float.class),
								@ColumnResult(name="l_refer", type=Float.class),
								@ColumnResult(name="l_ceiling", type=Float.class),
								@ColumnResult(name="l_floor", type=Float.class),
						})
		}
		)
@NamedNativeQuery(name = "PriceFluctuate.findPriceFluctuate",
	query = "SELECT" + 
			" T.stock AS t_stock," + 
			" ROUND(T.close,2) AS t_close," + 
			" ROUND(T.refer,2) AS t_refer," + 
			" ROUND(T.ceiling,2) AS t_ceiling," + 
			" ROUND(T.floor,2) AS t_floor," + 
			" IFNULL(ROUND(F.close,2),0) AS f_close," + 
			" IFNULL(ROUND(F.refer,2),0) AS f_refer," + 
			" IFNULL(ROUND(F.ceiling,2),0) AS f_ceiling," + 
			" IFNULL(ROUND(F.floor,2),0) AS f_floor," + 
			" ROUND(T.close - IFNULL(F.refer,T.close),2) AS c_volume," + 
			" ROUND((T.close - IFNULL(F.refer,T.close))*100/IFNULL(F.refer,T.close),2) AS c_percent," + 
			// 20200418 ThinhNH Add Start - column min_compare_close
			" ROUND(T.close - B.close,2) AS b_volume," + 
			" ROUND((T.close - B.close)*100/B.close,2) AS b_percent," + 
			// 20200418 ThinhNH Add End
			" V.avg_order AS v_order," + 
			" V.avg_total AS v_total," + 
			" A.date AS a_date," + 
			" ROUND(A.close,2) AS a_close," + 
			" ROUND(A.refer,2) AS a_refer," + 
			" ROUND(A.ceiling,2) AS a_ceiling," + 
			" ROUND(A.floor,2) AS a_floor," + 
			" B.date AS b_date," + 
			" ROUND(B.close,2) AS b_close," + 
			" ROUND(B.refer,2) AS b_refer," + 
			" ROUND(B.ceiling,2) AS b_ceiling," + 
			" ROUND(B.floor,2) AS b_floor," + 
			" H.date AS h_date," + 
			" ROUND(H.highest,2) AS h_highest," + 
			" ROUND(H.refer,2) AS h_refer," + 
			" ROUND(H.ceiling,2) AS h_ceiling," + 
			" ROUND(H.floor,2) AS h_floor," + 
			" L.date AS l_date," + 
			" ROUND(L.lowest,2) AS l_lowest," + 
			" ROUND(L.refer,2) AS l_refer," + 
			" ROUND(L.ceiling,2) AS l_ceiling," + 
			" ROUND(L.floor,2) AS l_floor " + 
			"FROM (SELECT" + 
			"  stock," + 
			"  close," + 
			"  refer," + 
			"  ceiling," + 
			"  floor" + 
			" FROM" + 
			"  prices" + 
			" WHERE" + 
			"  exchange = :exchange AND (:stock IS NULL OR stock = :stock) AND date = :date_to" + 
			") AS T" + 
			" LEFT JOIN (SELECT" + 
			"   stock," + 
			"   close," + 
			"   refer," + 
			"   ceiling," + 
			"   floor" + 
			"  FROM" + 
			"   prices" + 
			"  WHERE " + 
			"   exchange = :exchange AND (:stock IS NULL OR stock = :stock) AND date = :date_from" + 
			"  ) AS F" + 
			" ON F.stock = T.stock" + 
			" LEFT JOIN (SELECT" + 
			"   stock," + 
			"   ROUND(AVG(volume_order),0) avg_order," + 
			"   ROUND(AVG(volume_total),0) avg_total" + 
			"  FROM" + 
			"   prices" + 
			"  WHERE" + 
			"   exchange = :exchange AND (:stock IS NULL OR stock = :stock) AND date >= :date_from AND date <= :date_to" + 
			"  GROUP BY stock" + 
			"  ) AS V" + 
			" ON V.stock = T.stock" + 
			" LEFT JOIN (SELECT" + 
			"   PA.stock," + 
			"   PA.date," + 
			"   PA.close," + 
			"   PA.refer," + 
			"   PA.ceiling," + 
			"   PA.floor" + 
			"  FROM" + 
			"   prices AS PA" + 
			"   INNER JOIN (SELECT" + 
			"     stock," + 
			"     MAX(close) AS max_close" + 
			"    FROM" + 
			"     prices" + 
			"    WHERE" + 
			"     exchange = :exchange AND (:stock IS NULL OR stock = :stock) AND date >= :date_from AND date <= :date_to" + 
			"    GROUP BY stock" + 
			"    ) AS MX" + 
			"   ON MX.stock = PA.stock AND" + 
			"    MX.max_close = PA.close" + 
			"  WHERE" + 
			"   PA.exchange = :exchange AND (:stock IS NULL OR PA.stock = :stock) AND PA.date >= :date_from AND PA.date <= :date_to" + 
			"  GROUP BY PA.stock" + 
			"  ) AS A" + 
			" ON A.stock = T.stock" + 
			" LEFT JOIN (SELECT" + 
			"   PB.stock," + 
			"   PB.date," + 
			"   PB.close," + 
			"   PB.refer," + 
			"   PB.ceiling," + 
			"   PB.floor" + 
			"  FROM" + 
			"   prices AS PB" + 
			"   INNER JOIN (SELECT" + 
			"     stock," + 
			"     MIN(close) AS min_close" + 
			"    FROM" + 
			"     prices" + 
			"    WHERE" + 
			"     exchange = :exchange AND (:stock IS NULL OR stock = :stock) AND date >= :date_from AND date <= :date_to" + 
			"    GROUP BY stock" + 
			"    ) AS MN" + 
			"   ON MN.stock = PB.stock AND" + 
			"    MN.min_close = PB.close" + 
			"  WHERE" + 
			"   PB.exchange = :exchange AND (:stock IS NULL OR PB.stock = :stock) AND PB.date >= :date_from AND PB.date <= :date_to" + 
			"  GROUP BY PB.stock" + 
			"  ) AS B" + 
			" ON B.stock = T.stock" + 
			" LEFT JOIN (SELECT" + 
			"   HI.stock," + 
			"   HI.date," + 
			"   HI.highest," + 
			"   HI.refer," + 
			"   HI.ceiling," + 
			"   HI.floor" + 
			"  FROM" + 
			"   prices AS HI" + 
			"   INNER JOIN (SELECT" + 
			"     stock," + 
			"     MAX(highest) AS max_highest" + 
			"    FROM" + 
			"     prices" + 
			"    WHERE" + 
			"     exchange = :exchange AND (:stock IS NULL OR stock = :stock) AND date >= :date_from AND date <= :date_to" + 
			"    GROUP BY stock" + 
			"    ) AS MH" + 
			"   ON MH.stock = HI.stock AND" + 
			"    MH.max_highest = HI.highest" + 
			"  WHERE" + 
			"   HI.exchange = :exchange AND (:stock IS NULL OR HI.stock = :stock) AND HI.date >= :date_from AND HI.date <= :date_to" + 
			"  GROUP BY HI.stock" + 
			"  ) AS H" + 
			" ON H.stock = T.stock" + 
			" LEFT JOIN (SELECT" + 
			"   LO.stock," + 
			"   LO.date," + 
			"   LO.lowest," + 
			"   LO.refer," + 
			"   LO.ceiling," + 
			"   LO.floor" + 
			"  FROM" + 
			"   prices AS LO" + 
			"   INNER JOIN (SELECT" + 
			"     stock," + 
			"     MIN(lowest) AS min_lowest" + 
			"    FROM" + 
			"     prices" + 
			"    WHERE" + 
			"     exchange = :exchange AND (:stock IS NULL OR stock = :stock) AND date >= :date_from AND date <= :date_to" + 
			"    GROUP BY stock" + 
			"    ) AS ML" + 
			"   ON ML.stock = LO.stock AND" + 
			"    ML.min_lowest = LO.lowest" + 
			"  WHERE" + 
			"   LO.exchange = :exchange AND (:stock IS NULL OR LO.stock = :stock) AND LO.date >= :date_from AND LO.date <= :date_to" + 
			"  GROUP BY LO.stock" + 
			"  ) AS L" + 
			" ON L.stock = T.stock " + 
			"WHERE" + 
			" 1 = 1 " + 
			"ORDER BY" + 
			" c_percent DESC",
	resultSetMapping = "PriceFluctuateResult")
public class PriceFluctuate {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	private String t_stock;
	
	private Float t_close;
	
	private Float t_refer;
	
	private Float t_ceiling;
	
	private Float t_floor;
	
	private Float f_close;
	
	private Float f_refer;
	
	private Float f_ceiling;
	
	private Float f_floor;
	
	private Float c_volume;
	
	private Float c_percent;
	
	// 20200418 ThinhNH Add Start - column min_compare_close
	private Float b_volume;
	
	private Float b_percent;
	// 20200418 ThinhNH Add End
	
	private Double v_order;
	
	private Double v_total;
	
	private Date a_date;
	
	private Float a_close;
	
	private Float a_refer;
	
	private Float a_ceiling;
	
	private Float a_floor;

	private Date b_date;
	
	private Float b_close;
	
	private Float b_refer;
	
	private Float b_ceiling;
	
	private Float b_floor;
	
	private Date h_date;
	
	private Float h_highest;
	
	private Float h_refer;
	
	private Float h_ceiling;
	
	private Float h_floor;
	
	private Date l_date;
	
	private Float l_lowest;
	
	private Float l_refer;
	
	private Float l_ceiling;
	
	private Float l_floor;
	
	public PriceFluctuate(
			String t_stock, Float t_close, Float t_refer, Float t_ceiling, Float t_floor,
			Float f_close, Float f_refer, Float f_ceiling, Float f_floor,
			Float c_volume, Float c_percent, 
			// 20200418 ThinhNH Add Start - column min_compare_close
			Float b_volume, Float b_percent, 
			// 20200418 ThinhNH Add End
			Double v_order, Double v_total,
			Date a_date, Float a_close, Float a_refer, Float a_ceiling, Float a_floor,
			Date b_date, Float b_close, Float b_refer, Float b_ceiling, Float b_floor,
			Date h_date, Float h_highest, Float h_refer, Float h_ceiling, Float h_floor,
			Date l_date, Float l_lowest, Float l_refer, Float l_ceiling, Float l_floor) {
		this.t_stock = t_stock;
		this.t_close = t_close;
		this.t_refer = t_refer;
		this.t_ceiling = t_ceiling;
		this.t_floor = t_floor;
		this.f_close = f_close;
		this.f_refer = f_refer;
		this.f_ceiling = f_ceiling;
		this.f_floor = f_floor;
		this.c_volume = c_volume;
		this.c_percent = c_percent;
		// 20200418 ThinhNH Add Start - column min_compare_close
		this.b_volume = b_volume;
		this.b_percent = b_percent;
		// 20200418 ThinhNH Add End
		this.v_order = v_order;
		this.v_total = v_total;
		this.a_date = a_date;
		this.a_close = a_close;
		this.a_refer = a_refer;
		this.a_ceiling = a_ceiling;
		this.a_floor = a_floor;
		this.b_date = b_date;
		this.b_close = b_close;
		this.b_refer = b_refer;
		this.b_ceiling = b_ceiling;
		this.b_floor = b_floor;
		this.h_date = h_date;
		this.h_highest = h_highest;
		this.h_refer = h_refer;
		this.h_ceiling = h_ceiling;
		this.h_floor = h_floor;
		this.l_date = l_date;
		this.l_lowest = l_lowest;
		this.l_refer = l_refer;
		this.l_ceiling = l_ceiling;
		this.l_floor = l_floor;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getT_stock() {
		return t_stock;
	}

	public void setT_stock(String t_stock) {
		this.t_stock = t_stock;
	}

	public Float getT_close() {
		return t_close;
	}

	public void setT_close(Float t_close) {
		this.t_close = t_close;
	}

	public Float getT_refer() {
		return t_refer;
	}

	public void setT_refer(Float t_refer) {
		this.t_refer = t_refer;
	}

	public Float getT_ceiling() {
		return t_ceiling;
	}

	public void setT_ceiling(Float t_ceiling) {
		this.t_ceiling = t_ceiling;
	}

	public Float getT_floor() {
		return t_floor;
	}

	public void setT_floor(Float t_floor) {
		this.t_floor = t_floor;
	}

	public Float getF_close() {
		return f_close;
	}

	public void setF_close(Float f_close) {
		this.f_close = f_close;
	}

	public Float getF_refer() {
		return f_refer;
	}

	public void setF_refer(Float f_refer) {
		this.f_refer = f_refer;
	}

	public Float getF_ceiling() {
		return f_ceiling;
	}

	public void setF_ceiling(Float f_ceiling) {
		this.f_ceiling = f_ceiling;
	}

	public Float getF_floor() {
		return f_floor;
	}

	public void setF_floor(Float f_floor) {
		this.f_floor = f_floor;
	}

	public Float getC_volume() {
		return c_volume;
	}

	public void setC_volume(Float c_volume) {
		this.c_volume = c_volume;
	}

	public Float getC_percent() {
		return c_percent;
	}

	public void setC_percent(Float c_percent) {
		this.c_percent = c_percent;
	}

	// 20200418 ThinhNH Add Start - column min_compare_close
	public Float getB_volume() {
		return b_volume;
	}

	public void setB_volume(Float b_volume) {
		this.b_volume = b_volume;
	}
	
	public Float getB_percent() {
		return b_percent;
	}

	public void setB_percent(Float b_percent) {
		this.b_percent = b_percent;
	}
	// 20200418 ThinhNH Add End

	public Double getV_order() {
		return v_order;
	}

	public void setV_order(Double v_order) {
		this.v_order = v_order;
	}

	public Double getV_total() {
		return v_total;
	}

	public void setV_total(Double v_total) {
		this.v_total = v_total;
	}

	public Date getA_date() {
		return a_date;
	}

	public void setA_date(Date a_date) {
		this.a_date = a_date;
	}

	public Float getA_close() {
		return a_close;
	}

	public void setA_close(Float a_close) {
		this.a_close = a_close;
	}

	public Float getA_refer() {
		return a_refer;
	}

	public void setA_refer(Float a_refer) {
		this.a_refer = a_refer;
	}

	public Float getA_ceiling() {
		return a_ceiling;
	}

	public void setA_ceiling(Float a_ceiling) {
		this.a_ceiling = a_ceiling;
	}

	public Float getA_floor() {
		return a_floor;
	}

	public void setA_floor(Float a_floor) {
		this.a_floor = a_floor;
	}

	public Date getB_date() {
		return b_date;
	}

	public void setB_date(Date b_date) {
		this.b_date = b_date;
	}

	public Float getB_close() {
		return b_close;
	}

	public void setB_close(Float b_close) {
		this.b_close = b_close;
	}

	public Float getB_refer() {
		return b_refer;
	}

	public void setB_refer(Float b_refer) {
		this.b_refer = b_refer;
	}

	public Float getB_ceiling() {
		return b_ceiling;
	}

	public void setB_ceiling(Float b_ceiling) {
		this.b_ceiling = b_ceiling;
	}

	public Float getB_floor() {
		return b_floor;
	}

	public void setB_floor(Float b_floor) {
		this.b_floor = b_floor;
	}

	public Date getH_date() {
		return h_date;
	}

	public void setH_date(Date h_date) {
		this.h_date = h_date;
	}

	public Float getH_highest() {
		return h_highest;
	}

	public void setH_highest(Float h_highest) {
		this.h_highest = h_highest;
	}

	public Float getH_refer() {
		return h_refer;
	}

	public void setH_refer(Float h_refer) {
		this.h_refer = h_refer;
	}

	public Float getH_ceiling() {
		return h_ceiling;
	}

	public void setH_ceiling(Float h_ceiling) {
		this.h_ceiling = h_ceiling;
	}

	public Float getH_floor() {
		return h_floor;
	}

	public void setH_floor(Float h_floor) {
		this.h_floor = h_floor;
	}

	public Date getL_date() {
		return l_date;
	}

	public void setL_date(Date l_date) {
		this.l_date = l_date;
	}

	public Float getL_lowest() {
		return l_lowest;
	}

	public void setL_lowest(Float l_lowest) {
		this.l_lowest = l_lowest;
	}

	public Float getL_refer() {
		return l_refer;
	}

	public void setL_refer(Float l_refer) {
		this.l_refer = l_refer;
	}

	public Float getL_ceiling() {
		return l_ceiling;
	}

	public void setL_ceiling(Float l_ceiling) {
		this.l_ceiling = l_ceiling;
	}

	public Float getL_floor() {
		return l_floor;
	}

	public void setL_floor(Float l_floor) {
		this.l_floor = l_floor;
	}

	public String getClassChange() {
		if (Float.compare(this.c_volume,0) == 0) {
			return "reference";
		}
		if (Float.compare(this.c_volume,0) < 0) {
			return "reduce";
		}
		if (Float.compare(this.c_volume,0) > 0) {
			return "up";
		}
		return "";
	}
	
	// 20200418 ThinhNH Add Start - column min_compare_close
	public String getClassChangeB() {
		if (Float.compare(this.b_volume,0) == 0) {
			return "reference";
		}
		if (Float.compare(this.b_volume,0) < 0) {
			return "reduce";
		}
		if (Float.compare(this.b_volume,0) > 0) {
			return "up";
		}
		return "";
	}
	// 20200418 ThinhNH Add End
	
	public String getClassToClose() {
		if (Float.compare(this.t_close,this.t_refer) == 0) {
			return "reference";
		}
		if (Float.compare(this.t_close,this.t_ceiling) >= 0) {
			return "ceiling";
		}
		if (Float.compare(this.t_close,this.t_floor) <= 0) {
			return "floor";
		}
		if (Float.compare(this.t_close,this.t_floor) > 0 && Float.compare(this.t_close,this.t_refer) < 0) {
			return "reduce";
		}
		if (Float.compare(this.t_close,this.t_refer) > 0 && Float.compare(this.t_close,this.t_ceiling) < 0) {
			return "up";
		}
		return "";
	}
	
	public String getClassFromClose() {
		if (Float.compare(this.f_close,this.f_refer) == 0) {
			return "reference";
		}
		if (Float.compare(this.f_close,this.f_ceiling) >= 0) {
			return "ceiling";
		}
		if (Float.compare(this.f_close,this.f_floor) <= 0) {
			return "floor";
		}
		if (Float.compare(this.f_close,this.f_floor) > 0 && Float.compare(this.f_close,this.f_refer) < 0) {
			return "reduce";
		}
		if (Float.compare(this.f_close,this.f_refer) > 0 && Float.compare(this.f_close,this.f_ceiling) < 0) {
			return "up";
		}
		return "";
	}
	
	public String getClassMaxClose() {
		if (Float.compare(this.a_close,this.a_refer) == 0) {
			return "reference";
		}
		if (Float.compare(this.a_close,this.a_ceiling) >= 0) {
			return "ceiling";
		}
		if (Float.compare(this.a_close,this.a_floor) <= 0) {
			return "floor";
		}
		if (Float.compare(this.a_close,this.a_floor) > 0 && Float.compare(this.a_close,this.a_refer) < 0) {
			return "reduce";
		}
		if (Float.compare(this.a_close,this.a_refer) > 0 && Float.compare(this.a_close,this.a_ceiling) < 0) {
			return "up";
		}
		return "";
	}
	
	public String getClassMinClose() {
		if (Float.compare(this.b_close,this.b_refer) == 0) {
			return "reference";
		}
		if (Float.compare(this.b_close,this.b_ceiling) >= 0) {
			return "ceiling";
		}
		if (Float.compare(this.b_close,this.b_floor) <= 0) {
			return "floor";
		}
		if (Float.compare(this.b_close,this.b_floor) > 0 && Float.compare(this.b_close,this.b_refer) < 0) {
			return "reduce";
		}
		if (Float.compare(this.b_close,this.b_refer) > 0 && Float.compare(this.b_close,this.b_ceiling) < 0) {
			return "up";
		}
		return "";
	}
	
	public String getClassHighest() {
		if (Float.compare(this.h_highest,this.h_refer) == 0) {
			return "reference";
		}
		if (Float.compare(this.h_highest,this.h_ceiling) >= 0) {
			return "ceiling";
		}
		if (Float.compare(this.h_highest,this.h_floor) <= 0) {
			return "floor";
		}
		if (Float.compare(this.h_highest,this.h_floor) > 0 && Float.compare(this.h_highest,this.h_refer) < 0) {
			return "reduce";
		}
		if (Float.compare(this.h_highest,this.h_refer) > 0 && Float.compare(this.h_highest,this.h_ceiling) < 0) {
			return "up";
		}
		return "";
	}
	
	public String getClassLowest() {
		if (Float.compare(this.l_lowest,this.l_refer) == 0) {
			return "reference";
		}
		if (Float.compare(this.l_lowest,this.l_ceiling) >= 0) {
			return "ceiling";
		}
		if (Float.compare(this.l_lowest,this.l_floor) <= 0) {
			return "floor";
		}
		if (Float.compare(this.l_lowest,this.l_floor) > 0 && Float.compare(this.l_lowest,this.l_refer) < 0) {
			return "reduce";
		}
		if (Float.compare(this.l_lowest,this.l_refer) > 0 && Float.compare(this.l_lowest,this.l_ceiling) < 0) {
			return "up";
		}
		return "";
	}
}
