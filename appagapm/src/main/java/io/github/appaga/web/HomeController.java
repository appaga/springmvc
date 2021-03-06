package io.github.appaga.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.appaga.base.BaseController;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
	
	@GetMapping
	public String home(Model model) {
		model.addAttribute("pageTitle", "Home");
		return "/web/home";
	}
	
}
