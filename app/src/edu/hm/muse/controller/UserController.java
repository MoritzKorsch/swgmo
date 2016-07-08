package edu.hm.muse.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import stuff.SessionInfo;

@Controller
public class UserController {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@RequestMapping(value = "/user.secu", method = RequestMethod.GET)
	public ModelAndView createUser(
			) {
		ModelAndView mv = new ModelAndView("Users");
		
		return mv;
	}

}
