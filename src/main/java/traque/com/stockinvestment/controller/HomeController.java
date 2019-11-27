package traque.com.stockinvestment.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/")
public class HomeController {

	private static final Logger log = LogManager.getLogger(HomeController.class);
	
	@GetMapping("/")
	public String index(Model model) {
		log.info("HomeController index start.");
		return "index";
	}
}
