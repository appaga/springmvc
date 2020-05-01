package io.github.appaga.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.appaga.base.BaseController;

@Controller
@RequestMapping("/sample")
public class SampleController extends BaseController {
	
	@GetMapping("w3sample")
	public String w3sample(Model model) {
		model.addAttribute("pageTitle", "W3 샘플");
		return "/web/sample/w3sample";
	}
}
