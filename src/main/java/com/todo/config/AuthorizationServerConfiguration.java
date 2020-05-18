package com.todo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableAuthorizationServer
@EnableResourceServer
// @Order(Ordered.HIGHEST_PRECEDENCE)
public  class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter  {

	@Value("${authentication.oauth.clientId:todo}")
	private String clientId;

	@Value("${authentication.oauth.secret:secret}")
	private String clientSecret;

	@Value("${authentication.oauth.accessTokenValidititySeconds:3600}") // 12 hours
	private int accessTokenValiditySeconds;

	@Value("${authentication.oauth.refreshTokenValiditySeconds:36000}") // 30 days
	private int refreshTokenValiditySeconds;

@Autowired
public PasswordEncoder encoder;

	@Autowired
	public JwtAccessTokenConverter tokenEnhancer;

	@Autowired
	@Qualifier("betexJWTTokenStore")
	public JwtTokenStore tokenStore;

	@Autowired
	@Qualifier("tokenServices")
	public DefaultTokenServices tokenServices;

	@Autowired
	@Qualifier("jwtAccessTokenConverter")
	public JwtAccessTokenConverter tokenConverter;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("customerTokenEnhancer")
	private  TokenEnhancer customerTokenEnhancer;


	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customerTokenEnhancer, tokenEnhancer, tokenConverter));
		endpoints.tokenStore(tokenStore).tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {


		clients.inMemory()
				.withClient(clientId)
				.scopes("read", "write")
				.authorities(Authorities.ROLE_ADMIN.name(), Authorities.ROLE_USER.name())
				.authorizedGrantTypes("password", "refresh_token")
				.secret(encoder.encode(clientSecret))
				.accessTokenValiditySeconds(accessTokenValiditySeconds)
				.refreshTokenValiditySeconds(refreshTokenValiditySeconds);
	}



}

