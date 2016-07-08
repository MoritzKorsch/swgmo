package authentication;

import java.security.MessageDigest;
import java.util.Random;

import edu.hm.muse.exception.SuperFatalAndReallyAnnoyingException;

public class SaltAndPasswordShaker {
	
	public byte[] generate() {
		byte[] salt = new byte[32];
		new Random().nextBytes(salt);
		return salt;
	}
	
	public String hashPassword(String pass, byte[] salt) {
		String output;
		byte[] bpwd = pass.getBytes();
		byte[] saltedPw = new byte[bpwd.length + salt.length];
		try {

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			System.arraycopy(bpwd, 0, saltedPw, 0, bpwd.length);
			System.arraycopy(salt, 0, saltedPw, bpwd.length, salt.length);
			digest.update(saltedPw);
			byte[] md = digest.digest();
			output = byteToString(md);

		} catch (Exception e) {
			throw new SuperFatalAndReallyAnnoyingException("couldn't hash your password rip ");
		}
		return output;
	}
	
	public String byteToString(byte[] b) {
		String string;
		StringBuffer hex = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			hex.append(Integer.toHexString(b[i] & 0xff));
		}
		string = hex.toString();
		return string;

	}
}
