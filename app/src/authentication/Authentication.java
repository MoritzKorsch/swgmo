package authentication;

public class Authentication {

	public Authentication() {
		
	}
	
	public boolean authenticateToken(Token token1, Token token2) {
		if(token1 != null && token2 != null) {
			return token1.equals(token2);
		}
		else {
			throw new NullPointerException();
		}
	}
	
}
