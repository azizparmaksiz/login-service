package com.todo.service;

import com.todo.domain.User;
import com.todo.dto.RequestUpdateCustomerPassword;
import com.todo.dto.UserDTO;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface UserService {
    void save(UserDTO user);

    User findByUsername(String username);

    User getLoginUser();

    void changePassword(OAuth2Authentication authentication, RequestUpdateCustomerPassword passwordDto);
}