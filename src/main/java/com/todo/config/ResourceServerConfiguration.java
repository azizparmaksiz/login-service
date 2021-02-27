package com.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	@Qualifier("tokenServices")
	public DefaultTokenServices tokenServices;

	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	@Autowired
	private OAuthCorsFilter simpleCorsFilter;


	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		config.tokenServices(tokenServices);
	}
	
	 @Override
     public void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests().antMatchers("/", "/public/**", "/resources/**", "/resources/public/**").permitAll().and()
		 .csrf()
				 .disable()
				 .exceptionHandling()
				 .authenticationEntryPoint(customAuthenticationEntryPoint)
				 .and()
				 .logout()
				 .logoutUrl("/oauth/logout")
				 .logoutSuccessHandler(customLogoutSuccessHandler)
				 .and()
				 .addFilterBefore(simpleCorsFilter, ChannelProcessingFilter.class)

				 .csrf()
				 .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
				 .disable()
				 .headers()
				 .frameOptions()
				 .disable()
				 .and()
				 .authorizeRequests()
				 .antMatchers("/oauth/token**").permitAll()
				 .antMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
				 .antMatchers("/login**").authenticated()
					 .antMatchers("/customer**").authenticated();

	 }
	
}
