package edu.hm.muse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectController {
	
	@RequestMapping(value = "/projects.secu", method = RequestMethod.GET)
	public ModelAndView showProjectsOverview(HttpSession session) {
		ModelAndView mv = new ModelAndView("projects");
		
		return mv;
	}
	
	@RequestMapping(value = "/projectsCreate.secu", method = RequestMethod.GET)
	public ModelAndView createProject(HttpSession session) {
		ModelAndView mv = new ModelAndView("projectCreate");
		
		return mv;
	}
	
	@RequestMapping(value = "/projectEdit.secu", method = RequestMethod.GET)
	public ModelAndView editProject(HttpSession session) {
		ModelAndView mv = new ModelAndView("projectEdit");
		
		return mv;
	}
	
	@RequestMapping(value = "/projectDelete.secu", method = RequestMethod.POST)
	public ModelAndView deleteProject(HttpSession session){
		
		return new ModelAndView("redirect:projects.secu");
	}
}
