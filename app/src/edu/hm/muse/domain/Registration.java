package edu.hm.muse.domain;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import authentication.Authentication;
import authentication.SaltAndPasswordShaker;
import authentication.Token;
import stuff.PatternChecker;
import stuff.SessionInfo;

public class Registration {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@RequestMapping(value = "/registration.secu", method = RequestMethod.GET)
	public ModelAndView showRegistration(HttpSession session, SessionInfo sessioninfo) {
		ModelAndView mv = new ModelAndView("registration");
		Token token = new Token();
		mv.addObject("Token", token);
		session.setAttribute("Token", token);
		return mv;
	}
	
	@RequestMapping(value = "/registration.secu", method = RequestMethod.POST)
	public ModelAndView register(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "pass", required = false) String pass,
			@RequestParam(value = "passConf", required = false) String passConf,
			@RequestParam(value = "Token", required = false) Token token, HttpSession session, SessionInfo sessionInfo) {
		
		
		if (name == null || pass == null || passConf == null || name == "" || pass == "" || passConf == "" 
				|| !pass.equals(passConf)) {
			return returnToRegistration(session,"No empty fields allowed!");
		}
		
		else if (!(new PatternChecker(name).checkUserName())) return returnToRegistration(session, "Please don't use fancy symbols in the username!");
		else if (!(new PatternChecker(pass).checkPassword())) return returnToRegistration(session, "Please don't use fancy symbols in the password!");
		
		else if (null == token || !(new Authentication().authenticateToken((Token) session.getAttribute("Token"), token))) {
        	return returnToRegistration(session, "Authentication error!");
        }
		else {
			String sqlCount = "SELECT count(*) FROM M_USER WHERE muname = ?";
			int temp = 0;
			try {
				temp = jdbcTemplate.queryForInt(sqlCount, new Object[]{name});
			} catch (Exception e) {
				return returnToRegistration(session, "Something went wrong...");
			} 

			if (temp > 0) return returnToRegistration(session, "User already exists");
			
			String sqlInsertUser = "INSERT INTO USER(name, pwd, salt) VALUES(?, ?, ?)";
			SaltAndPasswordShaker snp = new SaltAndPasswordShaker();
			try {
				jdbcTemplate.update(sqlInsertUser, new Object[] {name, snp.byteToString(snp.generate())});
			} catch (Exception e) {
				return returnToRegistration(session, "Something bad happened...");
			}
		}
		
		//no instant sign in for security reasons
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("msg", "Login");
		mv.addObject("isLoggedIn", sessionInfo.isLoggedIn(session));
		
		return mv;
	}
	
    private ModelAndView returnToRegistration(HttpSession session, String msg) {
        ModelAndView mv = new ModelAndView("registration");
        mv.addObject("msg", msg);
        session.setAttribute("login", false);
        return mv;
    }
	
}
