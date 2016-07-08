package edu.hm.muse.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import authentication.Token;
import stuff.SessionInfo;

@Controller
public class UserController {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@RequestMapping(value = "/user.secu", method = RequestMethod.GET)
	public ModelAndView showProjectsOverview(
			@RequestParam(value = "Token", required = false) Token token, HttpSession session, SessionInfo sessionInfo) {
		
		//TODO do something here
		
		return new ModelAndView("texts");;
	}
	
	@RequestMapping(value = "/user.secu", method = RequestMethod.GET)
	public ModelAndView createUser(
			) {
		ModelAndView mv = new ModelAndView("Users");
		
		return mv;
	}

	// maybe do this in an interface
    private ModelAndView returnToLogin(HttpSession session, String msg) {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("msg", msg);
        session.setAttribute("login", false);
        return mv;
    }
}
