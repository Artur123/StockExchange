package at.jku.ce.brokerplatform;

public class WebUser {

	private String name;
	private String password;

	public WebUser(String name,String password) {
		this.name = name;
		this.password = password;
		Depot depot = new Depot();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
