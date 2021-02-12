package pro.artse.user.managers;

import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import javax.xml.bind.DatatypeConverter;

import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.models.User;
import pro.artse.user.notifications.NotificationStorage;

public class LoginManager implements ILoginManager {

	private static final Object changeLocalCredentialsFileLocker = new Object();

	@Override
	public boolean login(String token, String password) {
		try (BufferedReader reader = new BufferedReader(new FileReader(token + ".txt"))) {
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
				File file = new File(token + ".txt");
				file.createNewFile();
				try (PrintWriter fileStream = new PrintWriter(new FileOutputStream(file), true)) {
					fileStream.println(token + "#" + getHash(password));
				}
				User user = User.getInstance();
				user.setToken(token);
				user.setPasswordHash(getHash(password));
				user.setLoggedIn(true);
				user.setLoggedInAt(LocalDateTime.now());

				return NotificationStorage.createNotificationDirectory(User.getInstance().getToken());
			} catch (IOException e) {
				ErrorHandler.handle(e);
				return false;
			}
		}
	}

	private String getHash(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			return DatatypeConverter.printHexBinary(md.digest(password.getBytes())).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			ErrorHandler.handle(e);
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
		File file = new File(User.getInstance().getToken() + ".txt");
		try {
			Files.deleteIfExists(file.toPath());
			return NotificationStorage.deleteNotificationDirectory(User.getInstance().getToken());
		} catch (IOException e) {
			ErrorHandler.handle(e);
			return false;
		}
	}

	@Override
	public boolean changePassword(String oldPassword, String newPassword) {
		User currentUser = User.getInstance();

		// Check if credentials match
		if (!currentUser.getPasswordHash().equals(getHash(oldPassword)))
			return false;

		// Delete old information
		File file = new File(currentUser.getToken() + ".txt");
		try {
			Files.deleteIfExists(file.toPath());
			file.createNewFile();
		} catch (IOException e) {
			ErrorHandler.handle(e);
			return false;
		}

		// Update password
		try (PrintWriter fileStream = new PrintWriter(new FileOutputStream(file), true)) {
			fileStream.println(currentUser.getToken() + "#" + getHash(newPassword));
		} catch (FileNotFoundException e) {
			ErrorHandler.handle(e);
			return false;
		}
		currentUser.setPasswordHash(getHash(newPassword));
		return true;
	}
}
