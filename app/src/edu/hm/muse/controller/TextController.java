package edu.hm.muse.controller;

import java.sql.Types;
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
import edu.hm.muse.domain.Project;
import mapper.TextMapper;
import rowMapper.ProjectMapper;
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
	

	@RequestMapping(value = "/createtext.secu", method = RequestMethod.POST)
	public ModelAndView createText(@RequestParam(value = "name", required = false) String name, 
	@RequestParam(value="content", required = false) String content, 
	@RequestParam(value="projectID", required = false) int projectID, 
	@RequestParam(value="Token", required = false) Token token, HttpSession session, SessionInfo sessionInfo) {
		
		if(name == null || name.isEmpty() || projectID < 0 || content == null || content.isEmpty()){
			return returnToLogin(session, "Required Fields mustn't be empty!");
		} else if (null == token || !(new Authentication().authenticateToken((Token) session.getAttribute("Token"), token))) {
        	return returnToLogin(session, "Authentication error!");
        }
				
		String sqlText = "INSERT INTO TEXT (name, content, projectID) values(?, ?, ?)";
		try{
			jdbcTemplate.update(sqlText, new Object[] {name, content, projectID});
		}catch(Exception e){
			return new ModelAndView("redirect: errorpage.secu");
		}
		int projectid;
		int textid;
		try{
		String sqlprojectid = String.format("select * from PROJECT where muname = '%s' and project = ?", user.getUser(session));
		Project p = jdbcTemplate.queryForObject(sqlprojectid, new Object [] {projectID}, new int []{Types.VARCHAR}, new ProjectMapper());
		projectid = p.getId();	
		
		
		
		String sqltextid = String.format("select * from TEXT where muname = '%s' and textname = ?", user.getUser(session));
		Text t = jdbcTemplate.queryForObject(sqltextid, new Object [] {name}, new int []{Types.VARCHAR}, new TextMapper());
		textid = t.getId();	
		}catch(Exception e){
			return new ModelAndView("redirect: errorpage.secu");
		}
		
		try{
		String sqlPIT ="insert into PIT (projectID, textID, position) values(?, ?, ?)";
		jdbcTemplate.update(sqlPIT, new Object[] { projectid, textid, index});
		}catch(Exception e){
			return new ModelAndView("redirect: errorpage.secu");
		}
		
		
		
		
		
		
		
		
		
		return new ModelAndView("redirect:private.secu");
	}
		
	
	// maybe do this in an interface
    private ModelAndView returnToLogin(HttpSession session, String msg) {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("msg", msg);
        session.setAttribute("login", false);
        return mv;
    }

}
