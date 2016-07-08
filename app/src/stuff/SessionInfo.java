package stuff;

import javax.servlet.http.HttpSession;

public class SessionInfo {
	private boolean loggedIn;
	private String userName;
	private int userID;
	
	public SessionInfo() {}
	
	public boolean isLoggedIn(HttpSession session) {
		System.out.println(session.getAttribute("loggedIn"));
		if (session.getAttribute("loggedIn") != null || (Boolean)session.getAttribute("loggedIn") ) {
			this.loggedIn = true;
		}
		else{
			this.loggedIn = false;
		}
		return loggedIn;
	}
	
	public String getUserName(HttpSession session) {
		if (session.getAttribute("userName") == null){
			this.userName = "no user found";
		}else{
			this.userName = (String) session.getAttribute("userName");
		}
		return userName;
	}
	
	public int getUserID(HttpSession session) {
		if (session.getAttribute("userID") == null){
			this.userID = -1;
		}else{
			this.userID = (Integer) session.getAttribute("userID");
		}
		return userID;
	}
}
