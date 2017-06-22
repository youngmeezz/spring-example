package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.test.domain.EnvProfile;

@Configuration
public class ProfileConfig {
	@Bean
	@Profile("prod")
	public EnvProfile prodProfile() {
		EnvProfile profile  = new EnvProfile();
		profile.setEnvironmentProfile("prod");
		return profile;
	}
	
	@Bean
	@Profile("dev")
	public EnvProfile devProfile() {
		EnvProfile profile  = new EnvProfile();
		profile.setEnvironmentProfile("dev");
		return profile;
	}
}
