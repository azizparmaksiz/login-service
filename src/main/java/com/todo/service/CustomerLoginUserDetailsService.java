package com.todo.service;

import com.todo.constraints.MessageConstraints;
import com.todo.domain.User;
import com.todo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service()
public class CustomerLoginUserDetailsService implements UserDetailsService {
	private final Logger log = LoggerFactory.getLogger(CustomerLoginUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;




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
			grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()!=null?user.getRole().name():null));


		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);

	}
}
