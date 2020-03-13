package com.todo.config;

import com.todo.service.CustomerLoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Primary
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customerLoginUserDetailsService")
	private CustomerLoginUserDetailsService customerUserDetailsService;

	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Autowired
	private OAuthCorsFilter oAuthCorsFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customerUserDetailsService).passwordEncoder(passwordEncoder());

	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off

		http.authorizeRequests().antMatchers("/", "/public/**", "/resources/**", "/resources/public/**").permitAll()
				.and().csrf()
				.disable().exceptionHandling()
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.and()
				.addFilterBefore(oAuthCorsFilter, ChannelProcessingFilter.class)
				.csrf()
				.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
				.disable()
				.headers()
				.frameOptions()
				.disable()
				.and()
				.authorizeRequests().anyRequest().permitAll()
				.antMatchers("/login**").authenticated()
				.antMatchers("/customer**").authenticated();


	}

	@Bean()
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
