package stuff;

public class User {
	private final int id;
	private String uname, passwordHash;
	private final byte[] salt;
	
	public User(int id, String uname, String passwordHash, byte[] salt) {
		this.id = id;
		this.uname = uname;
		this.passwordHash = passwordHash;
		this.salt = salt;
	}
	
	public int getId() {
		return id;
	}
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setBody(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public byte[] getSalt() {
		return salt;
	}
	
}
