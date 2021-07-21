package sanitizerdispenser.model;

public class SuccessLogin {

	private String message;

	public String getMessage() {
		return message;
	}

	public SuccessLogin(String message) {
		super();
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SuccessLogin() {
		super();
	}
}
