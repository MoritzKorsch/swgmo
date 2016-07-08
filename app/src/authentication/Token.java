package authentication;

import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

public class Token {
	private final int value;
	
	public Token(HttpSession session) {
		this();
		setSessionToken(session);
	}
	public Token() {
		value = new SecureRandom().nextInt();
	}
	
	public int getValue() {
		return value;
	}
	
	public void setSessionToken(HttpSession session) {
		session.setAttribute("Token", getValue());
	}
	
	//TODO @Override public boolean equals(Object obj) {}
}
