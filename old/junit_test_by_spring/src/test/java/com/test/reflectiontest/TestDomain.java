package com.test.reflectiontest;

class TestDomainSuper {
	private String superField;

	public String getSuperField() {
		return superField;
	}

	public void setSuperField(String superField) {
		this.superField = superField;
	}
}

public class TestDomain extends TestDomainSuper {
	private String driver;
	private String url;
	private String username;
	private String password;
	
	public TestDomain() {
		
	}
	public TestDomain(String driver, String url, String username, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "TestDomain [driver=" + driver + ", url=" + url + ", username=" + username + ", password=" + password
				+ "]";
	}
	

}
