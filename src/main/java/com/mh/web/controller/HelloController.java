package com.mh.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView welcomPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("Title", "Hello World Spring Security Demo");
		model.addObject("username", "jason");
		model.setViewName("welcome");
		return model;
	}

	@RequestMapping(value = { "/admin**" }, method = RequestMethod.GET)
	public ModelAndView adminPage() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Title", "Welcom to admin page");
		map.put("username", "jasonyao");
		return new ModelAndView("admin", map);
	}

	@RequestMapping(value = { "/dba**" }, method = RequestMethod.GET)
	public ModelAndView dbaPage() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Title", "Welcom to admin page");
		map.put("username", "jasonyao-dba");
		return new ModelAndView("admin", map);
	}
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value="error",required=false) String error,
			@RequestParam(value="logout",required=false) String logout
			) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password.");
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;
	}

}
