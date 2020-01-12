package traque.com.stockinvestment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Department_Task")
public class DepartmentTask {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String name;
    
    private Integer rate;
    
    private Integer departmentEvaluationId;

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

	public Integer getDepartmentEvaluationId() {
		return departmentEvaluationId;
	}

	public void setDepartmentEvaluationId(Integer departmentEvaluationId) {
		this.departmentEvaluationId = departmentEvaluationId;
	}
}
