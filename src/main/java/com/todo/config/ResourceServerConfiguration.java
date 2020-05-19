package com.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Value("${config.oauth2.privateKey}")
	private String privateKey ;

	@Value("${config.oauth2.publicKey}")
	private String publicKey ;
	
	@Value("${authentication.oauth.accessTokenValiditityInSeconds}")
	private Integer PROP_TOKEN_VALIDITY_SECONDS;

	@Value("${authentication.oauth.refreshTokenValidityInSeconds}")
	private Integer REFRESH_PROP_TOKEN_VALIDITY_SECONDS;


	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	@Autowired
	private OAuthCorsFilter simpleCorsFilter;


	@Bean("jwtAccessTokenConverter")
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		// log.info("Initializing JWT with public key:\n" + publicKey);
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(privateKey);
		converter.setVerifierKey(publicKey);

		return converter;
	}

	@Bean("betexJWTTokenStore")
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Primary
	@Bean("tokenServices")
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setReuseRefreshToken(false);
		defaultTokenServices.setAccessTokenValiditySeconds(this.PROP_TOKEN_VALIDITY_SECONDS);
		defaultTokenServices.setRefreshTokenValiditySeconds(this.REFRESH_PROP_TOKEN_VALIDITY_SECONDS);
		defaultTokenServices.setTokenEnhancer(tokenEnhancer());
		return defaultTokenServices;
	}

	@Bean("customerTokenEnhancer")
	public TokenEnhancer tokenEnhancer() {
		return new CustomLoginTokenEnhancer();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		config.tokenServices(tokenServices());
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
//		 http
//				 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				 .and()
//				 .antMatcher("/**")
//				 .authorizeRequests()
//				 .antMatchers("/oauth/token**").permitAll()
//				 .antMatchers("/user/**").permitAll()
//				 .antMatchers("/login/**").authenticated()
//				 .antMatchers("/customer/**").authenticated()
//				 .anyRequest().authenticated()
//				 .and()
//				 .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);


	 }
	
}
