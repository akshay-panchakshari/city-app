
package com.city.app.security;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
		return (web) -> web.debug(securityDebug).ignoring().antMatchers("/h2-console/**");
	}

	@Bean
	public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        
        manager.createUser(User.withDefaultPasswordEncoder()
        		.username("user")
        		.password("password")
        		 .roles("USER")
        		 .build());
        /*
        manager.createUser(User.withUsername("user")
          .password(bCryptPasswordEncoder.encode("userPass"))
          .roles("USER")
          .build());
        manager.createUser(User.withUsername("admin")
          .password(bCryptPasswordEncoder.encode("adminPass"))
          .roles("ADMIN", "USER")
          .build());*/
        return manager;
	}
}
