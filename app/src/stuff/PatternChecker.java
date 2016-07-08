package stuff;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternChecker {
	private String toCheck;
	
	public PatternChecker(String suspect){
		this.toCheck = suspect;
	}
	
	public boolean checkUserName(){
		boolean check = false;
		Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
		Matcher match = pattern.matcher(toCheck);
		check = match.matches();
		return check;
	}
	
	public boolean checkPassword(){
		boolean check = false;
		Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct}).{8,25})");
		Matcher match = pattern.matcher(toCheck);
		check = match.matches();
		return check;
	}
	
}
