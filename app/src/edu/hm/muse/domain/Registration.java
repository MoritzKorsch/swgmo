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
	public ModelAndView register(@RequestParam(value = "mname", required = false) String mname,
			@RequestParam(value = "mpwd", required = false) String mpwd,
			@RequestParam(value = "vpwd", required = false) String vpwd,
			@RequestParam(value = "Token", required = false) Token token, HttpSession session, SessionInfo sessionInfo) {
		
		ModelAndView mv = new ModelAndView("registration");
		
		if (mname == null || mpwd == null || vpwd == null || mname == "" || mpwd == "" || vpwd == "" 
				|| !mpwd.equals(vpwd)) {
			return returnToRegistration(session,"Something went wrong...");
		}
		
		else if (!(new PatternChecker(mname).checkUserName())) return returnToRegistration(session, "Please don't use fancy symbols in the username!");
		else if (!(new PatternChecker(mpwd).checkPassword())) return returnToRegistration(session, "Please don't use fancy symbols in the password!");
		
		else if (null == token || !(new Authentication().authenticateToken((Token) session.getAttribute("Token"), token))) {
        	return returnToRegistration(session, "Authentication error!");
        }
		else {
			String sql = "SELECT count(*) FROM M_USER WHERE muname = ?";
			int temp = 0;
			try {
				temp = jdbcTemplate.queryForInt(sql, new Object[]{mname});
			} catch (Exception e) {
				return returnToRegistration(session, "Something went wrong...");
			} finally {
				if (temp > 0) return returnToRegistration(session, "User already exists");
				//TODO insert User with salt and hashed password
			}
		}
		
		
		return mv;
	}
	
    private ModelAndView returnToRegistration(HttpSession session, String msg) {
        ModelAndView mv = new ModelAndView("registration");
        mv.addObject("msg", msg);
        session.setAttribute("login", false);
        return mv;
    }
	
}
