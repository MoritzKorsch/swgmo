package edu.hm.muse.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import authentication.Authentication;
import authentication.Token;
import mapper.ProjectMapper;
import stuff.Project;
import stuff.SessionInfo;

@Controller
public class ProjectController {
	
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	@RequestMapping(value = "/projects.secu", method = RequestMethod.GET)
	public ModelAndView showProjectsOverview(@RequestParam(value = "name", required = false) String name, 
    		@RequestParam(value = "description", required = false) String description, 
    		@RequestParam(value = "Token", required = false) Token token, HttpSession session, SessionInfo sessionInfo) {
		
		ModelAndView mv = new ModelAndView("projects");
		
		if (null == name || name.isEmpty()) return returnToLogin(session, "Required Fields mustn't be empty!");
        else if (null == token || !(new Authentication().authenticateToken((Token) session.getAttribute("Token"), token))) {
        	return returnToLogin(session, "Authentication error!");
        }
		
		String sqlGetProjects = "SELECT * FROM PROJECTS WHERE ownerID = ?";
		List<Project> projectsForUser;

		try {
			projectsForUser = jdbcTemplate.query(sqlGetProjects, new Object[]{session.getAttribute("name")}, new ProjectMapper());
    	} catch (Exception e) {
    		return returnToLogin(session, "No chance for SQL injections!");
    	}
		if (projectsForUser.size() == 0) return returnToLogin(session, "No projects found!");

		mv.addObject("projCount", projectsForUser.size());
		mv.addObject("projects", projectsForUser);
		return mv;
	}
	
    private ModelAndView returnToLogin(HttpSession session, String msg) {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("msg", msg);
        session.setAttribute("login", false);
        return mv;
    }

	
	@RequestMapping(value = "/projectCreate.secu", method = RequestMethod.GET)
	public ModelAndView createProject(
			HttpSession session,
			@RequestParam(value = "msg", required = false) String msg
			) {
		ModelAndView mv = new ModelAndView("projectCreate");
		mv.addObject("msg", msg);
		return mv;
	}
	
	
	@RequestMapping(value = "/projectSave.secu", method = RequestMethod.POST)
	public ModelAndView saveProject(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = true) String desc,
			@RequestParam(value = "id", required = false) Integer id,
			HttpSession session,
			SessionInfo sessionInfo) {
		
		if(name == null || name == "" || name.isEmpty()) {
			return invalidForm("A project needs a name");
		}
		if(desc == null || desc == "" || desc.isEmpty()) {
			return invalidForm("A project needs a description");
		}
		
		ModelAndView mv = new ModelAndView("redirect:projects.secu");
		
		if(id == null) {	
			String sql = "INSERT INTO PROJECTS (name, description, ownerID) values (?, ?, ?)";
			try{
				jdbcTemplate.update( sql, new Object[] { name, desc, sessionInfo.getUserID(session) } );
			}catch(Exception e){
				//sollte geändert werden
				return new ModelAndView("redirect : errorpage.secu");
			}
			
			mv.addObject("msg", "Project created successfully");
		} else {
			String sql = "UPDATE PROJECTS SET name = ?, description = ? WHERE id = ?";
			try{
				jdbcTemplate.update( sql, new Object[] { name, desc, Integer.valueOf(id) } );
			}catch(Exception e){
				//sollte geändert werden
				return new ModelAndView("redirect : errorpage.secu");
			}
			
			mv.addObject("msg", "Project saved successfully");
		}
		
		
		
		return mv;
	}
	
	
	@RequestMapping(value = "/projectEdit.secu", method = RequestMethod.GET)
	public ModelAndView editProject(
			HttpSession session, 
			@RequestParam(value = "id", required = true) int id) {
		ModelAndView mv = new ModelAndView("projectEdit");
		
		String sql = "SELECT * FROM PROJECTS WHERE ID = id";
		List<Project> projects = jdbcTemplate.query( sql, new ProjectMapper() );
		System.out.println(projects);
		mv.addObject("project", projects.get(0));
		return mv;
	}
	
	
	@RequestMapping(value = "/projectDelete.secu", method = RequestMethod.POST)
	public ModelAndView deleteProject(
			HttpSession session, 
			@RequestParam(value = "id", required = true) int id){
		
		ModelAndView mv = new ModelAndView("redirect:projects.secu");
		
		try{
			String sql = "DELETE FROM PROJECTS WHERE ID = ?";
			jdbcTemplate.update(sql, new Object[] {Integer.valueOf(id)});
		}catch(Exception e){
			return new ModelAndView("redirect: errorpage.secu");
		}
		
		mv.addObject("msg", "Project deleted successfully");
		return mv;
	}
	
	
	private ModelAndView invalidForm(String msg) {
		ModelAndView mv = new ModelAndView("redirect:projectCreate.secu");	
		mv.addObject("msg", msg);	
		return mv;
	}
}
