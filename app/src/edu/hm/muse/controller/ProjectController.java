package edu.hm.muse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectController {
	@RequestMapping(value = "/projects.secu", method = RequestMethod.GET)
	public ModelAndView showProjectOverview(HttpSession session) {

		ModelAndView mv = new ModelAndView("examplepage");

		return mv;
	}

	@RequestMapping(value = "/projects.secu", method = RequestMethod.POST)
	public ModelAndView showSomeExamplePost(HttpSession session) {

		ModelAndView mv = new ModelAndView("examplepage");

		return mv;
	}
}
