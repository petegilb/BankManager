import java.util.*;

public class UserManagement {
	private HashMap<String, String> loginInfos;
	private HashMap<String, User> users;

	public UserManagement() {
		this.loginInfos = new HashMap<String, String>();
		this.users = new HashMap<String, User>();

		// initialize the manager here;
		loginInfos.put("admin", "12345");
		users.put("admin", new BankManager("admin", "12345"));
	}

	/*
	 * Given the login info, return the corresponding if exists
	 * GUI prints "invalid information"
	 */
	public User login(String username, String password) {
		User user = null;

		if (loginInfos.containsKey(username)) {
			if (loginInfos.get(username).equals(password)) {
				user = users.get(username);
			} else {
				//System.out.println("Wrong password");
			}
		} else {
			//System.out.println("User does not exist");
		}
		return user;
	}

	/*
	 * Create an user with given username and password
	 * GUI shows when username is taken
	 */
	public User createUser(String username, String password) {
		User user = null;

		if (loginInfos.containsKey(username)) {
			//System.out.println("User already exists");
		} else {
			loginInfos.put(username, password);
			user = new NormalUser(username, password);
			users.put(username, user);
		}

		return user;
	}

	//add a user to the storage
	public void addUser(User user){
		users.put(user.getUsername(), user);
		loginInfos.put(user.getUsername(), user.getPassword());
	}

	//get a user by their username
	public User getUser(String username){
		return users.get(username);
	}

	public HashMap<String, User> getUsers(){
		return users;
	}

	public void setUsers(HashMap<String, User> users){
		this.users = users;
	}
}
