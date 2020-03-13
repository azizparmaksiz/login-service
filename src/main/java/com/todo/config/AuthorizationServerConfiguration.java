package com.todo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
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
public  class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {

	private static final String ENV_OAUTH = "authentication.oauth.";
	private static final String PROP_CLIENTID = "clientid";
	private static final String PROP_SECRET = "secret";
	private static final String PROP_TOKEN_VALIDITY_SECONDS = "tokenValidityInSeconds";

	private RelaxedPropertyResolver propertyResolver;

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

		int tokenValidInSec=propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 1800);

		clients.inMemory().withClient(propertyResolver.getProperty(PROP_CLIENTID)).scopes("read", "write")
				.authorities(Authorities.ROLE_ADMIN.name(), Authorities.ROLE_USER.name()).authorizedGrantTypes("password", "refresh_token")
				.secret(propertyResolver.getProperty(PROP_SECRET))
				.accessTokenValiditySeconds(tokenValidInSec)
				.refreshTokenValiditySeconds(tokenValidInSec*10);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
	}

}

