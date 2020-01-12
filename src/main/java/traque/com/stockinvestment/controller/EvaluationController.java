package traque.com.stockinvestment.controller;

import java.util.ArrayList;
import java.util.Calendar;
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

import traque.com.stockinvestment.entity.DepartmentEvaluation;
import traque.com.stockinvestment.entity.DepartmentTask;
import traque.com.stockinvestment.model.DepartmentEvaluationRepository;
import traque.com.stockinvestment.model.DepartmentPointRepository;
import traque.com.stockinvestment.model.DepartmentRepository;
import traque.com.stockinvestment.model.DepartmentTaskRepository;
import traque.com.stockinvestment.model.DepartmentViewRepository;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

	private static final Logger log = LogManager.getLogger(EvaluationController.class);
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private DepartmentEvaluationRepository departmentEvaluationRepository;
	
	@Autowired
	private DepartmentTaskRepository departmentTaskRepository;
	
	@Autowired
	private DepartmentViewRepository departmentViewRepository;
	
	@Autowired
	private DepartmentPointRepository departmentPointRepository;
	
	@GetMapping("/department/create")
	public String departmentCreate(Model model) {
		log.info("Evaluation departmentCreate start.");
		model.addAttribute("departmentEvaluation", new DepartmentEvaluation());
		model.addAttribute("departments", departmentRepository.findAll());
		List<Integer> years = new ArrayList<Integer>();
		Calendar calendar = Calendar.getInstance();
		Integer year = calendar.get(Calendar.YEAR);
		years.add(year - 1);
		years.add(year);
		years.add(year + 1);
		model.addAttribute("years", years);
		return "evaluation/department/create";
	}
	
	@PostMapping("/department/insert")
	public String departmentInsert(@Valid DepartmentEvaluation departmentEvaluation, BindingResult result, Model model) {
		log.info("Evaluation departmentInsert start.");
		List<DepartmentEvaluation> departmentEvaluationExist = departmentEvaluationRepository.findByDepartmentIdAndYear(
				departmentEvaluation.getDepartmentId(), departmentEvaluation.getYear());
		if (!departmentEvaluationExist.isEmpty()) {
			result.rejectValue("departmentId", "error.departmentId", "Đã tồn tại bản đánh giá phòng ban cho năm " + departmentEvaluation.getYear());
		}
		if (result.hasErrors()) {
			model.addAttribute("departmentEvaluation", departmentEvaluation);
			model.addAttribute("departments", departmentRepository.findAll());
			List<Integer> years = new ArrayList<Integer>();
			Calendar calendar = Calendar.getInstance();
			Integer year = calendar.get(Calendar.YEAR);
			years.add(year - 1);
			years.add(year);
			years.add(year + 1);
			model.addAttribute("years", years);
			return "evaluation/department/create";
		}
		departmentEvaluationRepository.save(departmentEvaluation);
		model.addAttribute("departmentEvaluations", departmentEvaluationRepository.findAll());
		return "evaluation/department/list";
	}
	
	@GetMapping("/department/edit/{id}")
	public String departmentEdit(@PathVariable("id") Integer id, Model model) {
		log.info("Evaluation departmentEdit start.");
		model.addAttribute("departmentEvaluation", departmentEvaluationRepository.findById(id).get());
		model.addAttribute("departmentTask", new DepartmentTask());
		model.addAttribute("departmentTasks", departmentTaskRepository.findAll());
		return "evaluation/department/edit";
	}
	
	@PostMapping("/department/update")
	public String departmentUpdate(@Valid DepartmentTask departmentTask, BindingResult result, Model model) {
		log.info("Evaluation departmentUpdate start.");
		departmentTaskRepository.save(departmentTask);
		model.addAttribute("departmentEvaluation", departmentEvaluationRepository.findById(departmentTask.getDepartmentEvaluationId()).get());
		model.addAttribute("departmentTask", new DepartmentTask());
		model.addAttribute("departmentTasks", departmentTaskRepository.findAll());
		return "evaluation/department/edit";
	}
	
	@GetMapping("/department/delete/{id}")
	public String departmentDelete(@PathVariable("id") Integer id, Model model) {
		log.info("Evaluation departmentDelete start.");
		DepartmentEvaluation departmentEvaluation = departmentEvaluationRepository.findById(id).
				orElseThrow(() -> new IllegalArgumentException("Invalid Department Evaluation Id:" + id));
		departmentEvaluationRepository.delete(departmentEvaluation);
		model.addAttribute("departmentEvaluations", departmentEvaluationRepository.findAll());
		return "redirect:/evaluation/department/list";
	}
	
	@GetMapping("/department/list")
	public String departmentList(Model model) {
		log.info("Evaluation departmentList start.");
		model.addAttribute("departmentEvaluations", departmentEvaluationRepository.findAll());
		return "evaluation/department/list";
	}
	
	@GetMapping("/staff")
	public String staff(Model model) {
		log.info("Evaluation staff start.");
		return "evaluation/staff";
	}
	
	@GetMapping("/board")
	public String board(Model model) {
		log.info("Evaluation board start.");
		return "evaluation/board";
	}
}
