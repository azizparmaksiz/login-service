package com.eteshis.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.eteshis.constraints.MessageConstraints;
import com.eteshis.domain.User;
import com.eteshis.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customerLoginUserDetailsService")
public class CustomerLoginUserDetailsService implements UserDetailsService {
	private final Logger log = LoggerFactory.getLogger(CustomerLoginUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HttpServletRequest request;


	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		/**/

		String username = login.toLowerCase();


		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(MessageConstraints.USER_NOT_FOUND);
		}

		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//		List<String> roles = loginRepository.getRoles(customer.getCustomerId());
//		for (String role : roles) {
//			grantedAuthorities.add(new SimpleGrantedAuthority(role));
//		}


		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);

	}
}
