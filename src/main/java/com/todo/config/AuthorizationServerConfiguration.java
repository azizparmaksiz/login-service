package com.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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

import java.util.Arrays;


@Configuration
@EnableAuthorizationServer
@EnableResourceServer
// @Order(Ordered.HIGHEST_PRECEDENCE)
public  class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter  {
	@Value("${config.oauth2.privateKey}")
	private String privateKey ;

	@Value("${config.oauth2.publicKey}")
	private String publicKey ;
	@Value("${authentication.oauth.clientid}")
	private String clientId;

	@Value("${authentication.oauth.secret}")
	private String clientSecret;

	@Value("${authentication.oauth.accessTokenValiditityInSeconds:3600}") // 12 hours
	private int accessTokenValiditySeconds;

	@Value("${authentication.oauth.refreshTokenValidityInSeconds:36000}") // 30 days
	private int refreshTokenValiditySeconds;

   @Autowired
   public PasswordEncoder encoder;


	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("customerTokenEnhancer")
	private TokenEnhancer tokenEnhancer;


	@Bean("jwtAccessTokenConverter")
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		// log.info("Initializing JWT with public key:\n" + publicKey);
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(privateKey);
		converter.setVerifierKey(publicKey);

		return converter;
	}

	@Bean()
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
		defaultTokenServices.setAccessTokenValiditySeconds(this.accessTokenValiditySeconds);
		defaultTokenServices.setRefreshTokenValiditySeconds(this.refreshTokenValiditySeconds);
		defaultTokenServices.setTokenEnhancer(tokenEnhancer);
		return defaultTokenServices;
	}

	@Bean("customerTokenEnhancer")
	public TokenEnhancer customTokenEnhancer() {
		return new CustomLoginTokenEnhancer();
	}


	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer,  jwtAccessTokenConverter()));
		endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager);
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

