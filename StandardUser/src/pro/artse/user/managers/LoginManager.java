package pro.artse.user.managers;

import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import javax.xml.bind.DatatypeConverter;

import pro.artse.user.models.User;

public class LoginManager implements ILoginManager {

	private static final Object changeLocalCredentialsFileLocker = new Object();

	@Override
	public boolean login(String token, String password) {
		try (BufferedReader reader = new BufferedReader(new FileReader(token+".txt"))) {
			if (reader.lines().anyMatch(credentials -> check(token, password, credentials))) {
				User user = User.getInstance();
				user.setToken(token);
				user.setPasswordHash(getHash(password));
				user.setLoggedIn(true);
				user.setLoggedInAt(LocalDateTime.now());
				return true;
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveUser(String token, String password) {
		synchronized (changeLocalCredentialsFileLocker) {
			try {
				File file = new File(token+".txt");
				file.createNewFile();
				try (PrintWriter fileStream = new PrintWriter(new FileOutputStream(file), true)) {
					fileStream.println(token + "#" + getHash(password));
				}
				User user = User.getInstance();
				user.setToken(token);
				user.setPasswordHash(getHash(password));
				user.setLoggedIn(true);
				user.setLoggedInAt(LocalDateTime.now());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	private String getHash(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			return DatatypeConverter.printHexBinary(md.digest(password.getBytes())).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Boolean check(String token, String password, String fileLine) {
		String[] credentials = fileLine.split("#");
		return credentials[0].equals(token) && credentials[1].equals(getHash(password));
	}

	@Override
	public User getCurrentUser() {
		return User.getInstance();
	}

	@Override
	public void logout() {
		User user = User.getInstance();
		user.setToken(null);
		user.setPasswordHash(null);
		user.setLoggedIn(false);
		user.setLoggedInAt(null);
	}

	@Override
	public boolean removeUser() {
		File file = new File(User.getInstance().getToken()+".txt");
		try {
			Files.deleteIfExists(file.toPath());
			logout();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
