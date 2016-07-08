/*
 * **
 *  *                                        __          ____                                     __
 *  *     /'\_/`\                 __        /\ \        /\  _`\                                __/\ \__
 *  *    /\      \  __  __   ___ /\_\    ___\ \ \___    \ \,\L\_\     __    ___  __  __  _ __ /\_\ \ ,_\  __  __
 *  *    \ \ \__\ \/\ \/\ \/' _ `\/\ \  /'___\ \  _ `\   \/_\__ \   /'__`\ /'___\\ \/\ \/\`'__\/\ \ \ \/ /\ \/\ \
 *  *     \ \ \_/\ \ \ \_\ \\ \/\ \ \ \/\ \__/\ \ \ \ \    /\ \L\ \/\  __//\ \__/ \ \_\ \ \ \/ \ \ \ \ \_\ \ \_\ \
 *  *      \ \_\\ \_\ \____/ \_\ \_\ \_\ \____\\ \_\ \_\   \ `\____\ \____\ \____\ \____/\ \_\  \ \_\ \__\\/`____ \
 *  *       \/_/ \/_/\/___/ \/_/\/_/\/_/\/____/ \/_/\/_/    \/_____/\/____/\/____/\/___/  \/_/   \/_/\/__/ `/___/> \
 *  *                                                                                                         /\___/
 *  *                                                                                                         \/__/
 *  *
 *  *     ____                                               __          ____
 *  *    /\  _`\                                            /\ \        /\  _`\
 *  *    \ \ \L\ \     __    ____    __     __     _ __  ___\ \ \___    \ \ \L\_\  _ __  ___   __  __  _____
 *  *     \ \ ,  /   /'__`\ /',__\ /'__`\ /'__`\  /\`'__\'___\ \  _ `\   \ \ \L_L /\`'__\ __`\/\ \/\ \/\ '__`\
 *  *      \ \ \\ \ /\  __//\__, `\\  __//\ \L\.\_\ \ \/\ \__/\ \ \ \ \   \ \ \/, \ \ \/\ \L\ \ \ \_\ \ \ \L\ \
 *  *       \ \_\ \_\ \____\/\____/ \____\ \__/.\_\\ \_\ \____\\ \_\ \_\   \ \____/\ \_\ \____/\ \____/\ \ ,__/
 *  *        \/_/\/ /\/____/\/___/ \/____/\/__/\/_/ \/_/\/____/ \/_/\/_/    \/___/  \/_/\/___/  \/___/  \ \ \/
 *  *                                                                                                    \ \_\
 *  *    This file is part of BREW.
 *  *
 *  *    BREW is free software: you can redistribute it and/or modify
 *  *    it under the terms of the GNU General Public License as published by
 *  *    the Free Software Foundation, either version 3 of the License, or
 *  *    (at your option) any later version.
 *  *
 *  *    BREW is distributed in the hope that it will be useful,
 *  *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *    GNU General Public License for more details.
 *  *
 *  *    You should have received a copy of the GNU General Public License
 *  *    along with BREW.  If not, see <http://www.gnu.org/licenses/>.                                                                                                  \/_/
 *
 */

package edu.hm.muse.controller;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

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
import authentication.SaltAndPasswordShaker;
import authentication.Token;
import mapper.UserMapper;
import stuff.SessionInfo;
import stuff.User;

@Controller
public class LoginController {

    private JdbcTemplate jdbcTemplate;
    
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/login.secu", method = RequestMethod.GET)
    public ModelAndView showLoginScreen(HttpSession session, SessionInfo sessionInfo) {
    	
        ModelAndView mv = new ModelAndView("login");
        Token token = new Token(session);
        
        mv.addObject("msg", "Enter name and password");
        mv.addObject("isLoggedIn", sessionInfo.isLoggedIn(session));
        mv.addObject("mname", "");
        mv.addObject("Token", token);
        return mv;
    }

    @RequestMapping(value = "/login.secu", method = RequestMethod.POST)
    public ModelAndView doSomeLogin(@RequestParam(value = "name", required = false) String name, 
    		@RequestParam(value = "pass", required = false) String pass, 
    		@RequestParam(value = "Token", required = false) Token token, HttpSession session, SessionInfo sessionInfo) {
        
    	if (null == name || null == pass || name.isEmpty() || pass.isEmpty()) {
            return returnToLogin(session, "Required Fields mustn't be empty!");
    		//throw new SuperFatalAndReallyAnnoyingException("required fields must not be empty!");
        } else if (null == token || !(new Authentication().authenticateToken((Token) session.getAttribute("Token"), token))) {
        	return returnToLogin(session, "Authentication error!");
        }
    	
    	String sqlGetUser = "SELECT * FROM USER WHERE name = ?";
    	User temp;
    	try {
    		temp = jdbcTemplate.queryForObject(sqlGetUser, new Object[]{name}, new UserMapper());
    	} catch (Exception e) {
    		return returnToLogin(session, "No chance for SQL injections!");
    	}
		if (temp == null) {
			return returnToLogin(session, "User not found!");
		}
		temp.getPasswordHash();
		temp.getSalt();
		if (!(temp.getPasswordHash().equals(new SaltAndPasswordShaker().hashPassword(pass, temp.getSalt())))) {
			return returnToLogin(session, "Password wrong!");
		}
		//TODO implement brute force block?
		
		//authenticated
		else {
			session.setAttribute("user", name);
			session.setAttribute("login", true);
			return new ModelAndView("redirect:index.secu");
		}
    }

    private ModelAndView returnToLogin(HttpSession session, String msg) {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("msg", msg);
        session.setAttribute("login", false);
        return mv;
    }

    public static String calculateSHA256(InputStream is) {
        String output;
        int read;
        byte[] buffer = new byte[8192];
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] hash = digest.digest();
            BigInteger bigInt = new BigInteger(1, hash);
            output = bigInt.toString(16);
        }
        catch (Exception e) {
            e.printStackTrace( System.err );
            return "0";
        }
        return output;
    }

}
