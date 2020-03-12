package com.eteshis.service;

import com.eteshis.constraints.MessageConstraints;
import com.eteshis.domain.User;
import com.eteshis.dto.RequestUpdateCustomerPassword;
import com.eteshis.dto.UserDTO;
import com.eteshis.exception.DuplicateException;
import com.eteshis.exception.NotFoundException;
import com.eteshis.exception.UnAuthorizedOperationException;
import com.eteshis.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passencoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(UserDTO userDto) {

        User user=userRepository.findByUsername(userDto.getUsername());
        if(user!=null){
            throw new DuplicateException(MessageConstraints.USERNAME_DUPLICATED);
        }

         user=modelMapper.map(userDto,User.class);

        user.setPassword(passencoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public User getLoginUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException(MessageConstraints.USER_TOKEN_NOT_VALID);
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException(MessageConstraints.USER_NOT_FOUND);
        }

        return user;
    }

    @Override
    public void changePassword(OAuth2Authentication authentication, RequestUpdateCustomerPassword passwordDto) {

        String username = (String) authentication.getPrincipal();

        if (StringUtils.isEmpty(username)) {
            throw new UnAuthorizedOperationException(MessageConstraints.USER_TOKEN_NOT_VALID);
        }

        User customer=userRepository.findByUsername(username);

        if (customer == null) {
            throw new NotFoundException(MessageConstraints.USER_NOT_FOUND);
        }

        String encodedOldPassword=customer.getPassword();

        if(!passencoder.matches(passwordDto.getOldPassword(),encodedOldPassword)){
            throw new UnAuthorizedOperationException(MessageConstraints.OLD_PASSWORD_NOT_MATCHED);
        }


        String encodedPassword=passencoder.encode(passwordDto.getNewPassword());

        customer.setPassword(encodedPassword);
        userRepository.save(customer);
    }

}