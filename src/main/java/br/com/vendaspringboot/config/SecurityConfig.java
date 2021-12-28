package br.com.vendaspringboot.config;

import org.hibernate.boot.internal.MetadataBuildingContextRootImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.passwordEncoder(passwordEncoder())
			.withUser("admin")
			.password(passwordEncoder().encode("123"))
			.roles("USERS");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//autenticação http basic
		http
			.csrf()
			.disable()
			.authorizeRequests()
				.antMatchers("api/clientes/**").hasRole("USERS")	
			.and()
			.authorizeRequests()
				.antMatchers("api/produtos/**").hasRole("ADMIN")	
			.and()			
			.authorizeRequests()
				.antMatchers("api/pedidos/**").hasRole("USERS")	
			.and()
			.httpBasic();
	}
}
