package com.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("## [init security config]");
		http.authorizeRequests()
				.antMatchers("/resources/**", "/signup", "/about").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll()
				.and()
			.formLogin()
				.loginPage("/login")				
				.loginProcessingUrl("/loginPost")
				.usernameParameter("loginid")
				.passwordParameter("password")				
				.permitAll()				
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.and()
			.csrf().disable()
			.httpBasic();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {	
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("admin").password("admin").roles("ADMIN").build());
		return manager;
	}

}
