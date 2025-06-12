package dev.school.app.biblioapp.models;

import dev.school.app.biblioapp.Main;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManager {

	private static final Logger LOGGER = Main.getLogger();
	private final List<User> users;
	private final String xmlPath;

	public UserManager(String xmlPath) {
		this.xmlPath = xmlPath;
		users = new ArrayList<>();
		loadUsers(xmlPath);
	}

	private void loadUsers(String path) {
		try {
			File xmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			NodeList userNodes = doc.getElementsByTagName("user");
			for (int i = 0; i < userNodes.getLength(); i++) {
				Element userEl = (Element) userNodes.item(i);
				String username = userEl.getAttribute("username");
				String password = userEl.getAttribute("password");
				boolean admin = Boolean.parseBoolean(userEl.getAttribute("admin"));
				users.add(new User(username, password, admin));
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public User authenticate(String username, String password) {
		for (User u : users) {
			if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
				return u;
			}
		}
		return null;
	}

	public List<User> getAllUsers() {
		return new ArrayList<>(users);
	}

	public void saveUsers(List<User> usersToSave) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element root = doc.createElement("users");
			doc.appendChild(root);

			for (User user : usersToSave) {
				Element userElement = doc.createElement("user");
				userElement.setAttribute("username", user.getUsername());
				userElement.setAttribute("password", user.getPassword());
				userElement.setAttribute("admin", String.valueOf(user.isAdmin()));
				root.appendChild(userElement);
			}

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource domSource = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xmlPath));
			transformer.transform(domSource, result);

			this.users.clear();
			this.users.addAll(usersToSave);

			LOGGER.log(Level.INFO, "Users saved successfully.");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
