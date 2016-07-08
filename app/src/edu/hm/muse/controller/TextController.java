package edu.hm.muse.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import authentication.Authentication;
import authentication.Token;
import mapper.TextMapper;
import stuff.SessionInfo;
import stuff.Text;

public class TextController {
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@RequestMapping(value = "/texts.secu", method = RequestMethod.GET)
	public ModelAndView showTextsOverview(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "projectRequestNo", required = false) int projectRequestNo,
    		@RequestParam(value = "content", required = false) String content, 
    		@RequestParam(value = "Token", required = false) Token token, HttpSession session, SessionInfo sessionInfo) {
		
		ModelAndView mv = new ModelAndView("texts");

		if (null == name || name.isEmpty()) return returnToLogin(session, "Required Fields mustn't be empty!");
        else if (null == token || !(new Authentication().authenticateToken((Token) session.getAttribute("Token"), token))) {
        	return returnToLogin(session, "Authentication error!");
        }
		
		String sqlGetTexts = "SELECT * FROM PROJECTS WHERE projectID = ?";
		List<Text> textsForProject;
		
		try {
			textsForProject = jdbcTemplate.query(sqlGetTexts, new Object[]{session.getAttribute("projectRequestNo")}, new TextMapper());
    	} catch (Exception e) {
    		return returnToLogin(session, "No chance for SQL injections!");
    	}
		if (textsForProject.size() == 0) return returnToLogin(session, "No projects found!");

		//TODO do something here
		
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
