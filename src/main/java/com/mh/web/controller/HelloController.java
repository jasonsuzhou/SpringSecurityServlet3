package com.mh.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
			@RequestParam(value="logout",required=false) String logout,
			HttpServletRequest request
			) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		}
		model.setViewName("403");
		return model;
	}
	
	private String getErrorMessage(HttpServletRequest request, String key) {
		Exception exception = (Exception)request.getSession().getAttribute(key);
		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password.";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password.";
		}
		return error;
	}

}
