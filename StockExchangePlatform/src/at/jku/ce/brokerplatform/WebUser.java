package at.jku.ce.brokerplatform;

public class WebUser {

	private String name;

	public WebUser(String name) {
		this.name = name;

		Depot depot = new Depot();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
