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
	public ModelAndView showProjectsOverview(HttpSession session, SessionInfo info) {
		ModelAndView mv = new ModelAndView("projects");
		
		// TODO: userID über info.getUserID(session) is statement aufnehmen
		String sql = "SELECT count(*) FROM PROJECTS";
		
		int projCount = jdbcTemplate.queryForInt(sql);
		
		if(projCount == 0) {
			mv.addObject("projCount", projCount);
			return mv;
		}
		
		sql = "SELECT * FROM PROJECTS";
		List<Project> projects = jdbcTemplate.query( sql, new ProjectMapper() );
		
		mv.addObject("projCount", projCount);
		mv.addObject("projects", projects);
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
			SessionInfo info
			) {
		
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
				jdbcTemplate.update( sql, new Object[] { name, desc, info.getUserID(session) } );
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
