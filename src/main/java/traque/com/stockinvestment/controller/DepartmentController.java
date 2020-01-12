package traque.com.stockinvestment.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import traque.com.stockinvestment.entity.Department;
import traque.com.stockinvestment.model.DepartmentRepository;

@Controller
@RequestMapping("/department")
public class DepartmentController {

	private static final Logger log = LogManager.getLogger(DepartmentController.class);
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@GetMapping("/create")
	public String create(Model model) {
		log.info("Department create start.");
		model.addAttribute("department", new Department());
		return "department/create";
	}
	
	@PostMapping("/insert")
	public String insert(@Valid Department department, BindingResult result, Model model) {
		log.info("Department insert start.");
		List<Department> departmentExistName = departmentRepository.findByName(department.getName().trim());
		if (!departmentExistName.isEmpty()) {
			result.rejectValue("name", "error.name", "Tên phòng ban đã tồn tại trong cơ sở dữ liệu");
		}
		if (result.hasErrors()) {
			model.addAttribute("department", department);
			return "department/create";
		}
		department.setName(department.getName().trim());
		departmentRepository.save(department);
		model.addAttribute("departments", departmentRepository.findAll());
		return "department/list";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, Model model) {
		log.info("Department delete start.");
		Department department = departmentRepository.findById(id).
				orElseThrow(() -> new IllegalArgumentException("Invalid Department Id:" + id));
		departmentRepository.delete(department);
		model.addAttribute("departments", departmentRepository.findAll());
		return "redirect:/department/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		log.info("Department list start.");
		model.addAttribute("departments", departmentRepository.findAll());
		return "department/list";
	}
}
