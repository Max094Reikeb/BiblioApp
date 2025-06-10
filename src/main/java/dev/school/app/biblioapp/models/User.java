package dev.school.app.biblioapp.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe-type d'un utilisateur de l'application.
 */
public class User {
	private final StringProperty username;
	private final StringProperty password;
	private final BooleanProperty isAdmin;

	/**
	 * Initialisation d'un utilisateur-type de l'application.
	 *
	 * @param username Nom de l'utilisateur.
	 * @param password Mot de passe de l'utilisateur.
	 * @param isAdmin  Est-ce que l'utilisateur est administrateur ou non.
	 */
	public User(String username, String password, boolean isAdmin) {
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.isAdmin = new SimpleBooleanProperty(isAdmin);
	}

	/**
	 * Getter public du nom de l'utilisateur.
	 *
	 * @return Le nom de l'utilisateur.
	 */
	public String getUsername() {
		return username.get();
	}

	/**
	 * Getter public du mot de passe de l'utilisateur.
	 *
	 * @return Le mot de passe de l'utilisateur.
	 */
	public String getPassword() {
		return password.get();
	}

	/**
	 * Getter public de si l'utilisateur est administrateur ou non.
	 *
	 * @return Si l'utilisateur est administrateur ou non.
	 */
	public boolean isAdmin() {
		return isAdmin.get();
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return Le nom de l'utilisateur.
	 */
	public StringProperty usernameProperty() {
		return username;
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return Le mot de passe de l'utilisateur.
	 */
	public StringProperty passwordProperty() {
		return password;
	}

	/**
	 * Getter bindé pour JavaFX
	 *
	 * @return Si l'utilisateur est administrateur ou non.
	 */
	public BooleanProperty isAdminProperty() {
		return isAdmin;
	}

	/**
	 * Fonction permettant d'obtenir les informations d'un utilisateur à partir de celui-ci.
	 *
	 * @return Un string avec les informations de l'utilisateur.
	 */
	@Override
	public String toString() {
		return "User{" +
				"username='" + getUsername() + '\'' +
				", password='" + getPassword() + '\'' +
				", isAdmin=" + isAdmin() +
				'}';
	}
}
