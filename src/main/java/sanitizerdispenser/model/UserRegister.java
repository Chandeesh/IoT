package sanitizerdispenser.model;

import org.springframework.data.annotation.Id;

public class UserRegister {

	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private boolean enabled;
	private String token;
	private String time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public UserRegister(String name, String email, String password, boolean enabled, String token, String time) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.token = token;
		this.time = time;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public UserRegister(String id, String name, String email, String password, boolean enabled, String token,
			String time) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.token = token;
		this.time = time;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String isToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public UserRegister() {
		super();
	}
	
}