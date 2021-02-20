package org.hassen.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// demande à spring d'utiliser la base de donnée deja mentionné en application.properties 
	@Autowired
	private DataSource dataSource;

	@SuppressWarnings("deprecation")
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/* basique authentification used par default par spring security
		auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN","USER");
		auth.inMemoryAuthentication().withUser("user").password("{noop}5678").roles("USER");
		*/
		
		// login as principal == spring security connait que principal est username
		// pass as credentials == spring indique que pass est une password
		// role as role == spring indique que role est un role
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select login as principal, pass as credentials , active from users where login=?")
		.authoritiesByUsernameQuery("select login as principal, role as role from users_roles where login=?")
		.passwordEncoder(new MessageDigestPasswordEncoder("MD5"))
		.rolePrefix("ROLE_");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.formLogin().loginPage("/login"); // ceci pour dire à spring security que l'operation d'authentification	passe forcement par un formulaire d'authentification
		// spring Security utilise par default un formulaire d'authentification.
		http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
		http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");
		
	}
	
	
}
