package com.test.domain;

public class EnvProfile {
	private String environmentProfile;

	public String getEnvironmentProfile() {
		return environmentProfile;
	}

	public void setEnvironmentProfile(String environmentProfile) {
		this.environmentProfile = environmentProfile;
	}

	@Override
	public String toString() {
		return "Profile [environmentProfile=" + environmentProfile + "]";
	}	
}
