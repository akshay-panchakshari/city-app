
package com.city.app.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${spring.security.debug:false}")
    boolean securityDebug;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.cors().and()
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/api/cities").authenticated()
			.and()
			.httpBasic()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		return http.build();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.debug(securityDebug).ignoring().antMatchers("/h2-console/**","/api/login");
	}

	@Bean
	public UserDetailsService userDetailsService() {
        List<UserDetails> userDetailsList = new ArrayList<>();
        UserDetails admin = User.withUsername("admin")
		.password("adminpass")
		.roles("ALLOW_EDIT")
		.build();
        
        UserDetails user = User.withUsername("user")
        		.password("userpass")
        		.roles("READ_ONLY")
        		.build();
          
        userDetailsList.add(admin);
        userDetailsList.add(user);
        return new InMemoryUserDetailsManager(userDetailsList);
       
	}
	@Bean
	public PasswordEncoder passwordEncoder () {
		return NoOpPasswordEncoder.getInstance();
	}
}
