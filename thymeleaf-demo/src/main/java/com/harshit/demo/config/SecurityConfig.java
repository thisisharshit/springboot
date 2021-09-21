package com.harshit.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("securityDataSource")
	private DataSource securityDataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http.authorizeRequests()
				.antMatchers("/").hasRole("EMPLOYEE")
				.antMatchers("/employees/list").hasRole("EMPLOYEE")
				.antMatchers("/employees/showFormForAdd").hasRole("MANAGER")
				.antMatchers("/employees/save").hasRole("MANAGER")
				.antMatchers("/employees/showFormForUpdate").hasRole("MANAGER")
				.antMatchers("/employees/delete").hasRole("MANAGER")
			.and()
			.formLogin()
				.loginPage("/employees/login")
				.loginProcessingUrl("/authenticateUser")
				.permitAll()
			.and()
				.logout().permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/employees/access-denied");
		
//		http.authorizeRequests()
//		.antMatchers("/showFormAdd").hasRole("MANAGER")
//		.antMatchers("/save").hasRole("MANAGER")
//		.antMatchers("/showFormUpdate").hasRole("MANAGER")
//		.antMatchers("/delete").hasRole("MANAGER")
//		.antMatchers("/resources/**").permitAll()
//		.and()
//		.formLogin()
//			.permitAll()
//		.and()
//		.logout().permitAll()
//		.and()
//		.exceptionHandling().accessDeniedPage("/access-denied");
		
		http
        //HTTP Basic authentication
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
        .antMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
        .antMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
        .and()
        .csrf().disable()
        .formLogin();
		
	}

	
	
}
