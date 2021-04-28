public abstract class User {
	protected String username;
	protected String password;
	protected UserType type;

	public User (String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword(){
		return password;
	}

	public UserType getType(){
		return this.type;
	}

	public String toString() {
		return username;
	}
}
