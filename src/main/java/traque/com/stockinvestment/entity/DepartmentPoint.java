package traque.com.stockinvestment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Department_Point")
public class DepartmentPoint {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String name;
    
    private Integer rate;
    
    private Integer point_d;
    
    private Integer point_c;
    
    private Integer point_s;
    
    private String guide1;
    
    private String guide2;
    
    private Integer department_view_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getPoint_d() {
		return point_d;
	}

	public void setPoint_d(Integer point_d) {
		this.point_d = point_d;
	}

	public Integer getPoint_c() {
		return point_c;
	}

	public void setPoint_c(Integer point_c) {
		this.point_c = point_c;
	}

	public Integer getPoint_s() {
		return point_s;
	}

	public void setPoint_s(Integer point_s) {
		this.point_s = point_s;
	}

	public String getGuide1() {
		return guide1;
	}

	public void setGuide1(String guide1) {
		this.guide1 = guide1;
	}

	public String getGuide2() {
		return guide2;
	}

	public void setGuide2(String guide2) {
		this.guide2 = guide2;
	}

	public Integer getDepartment_view_id() {
		return department_view_id;
	}

	public void setDepartment_view_id(Integer department_view_id) {
		this.department_view_id = department_view_id;
	}
}
