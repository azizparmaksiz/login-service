package com.eteshis.service;

import com.eteshis.domain.User;
import com.eteshis.dto.RequestUpdateCustomerPassword;
import com.eteshis.dto.UserDTO;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface UserService {
    void save(UserDTO user);

    User findByUsername(String username);

    User getLoginUser();

    void changePassword(OAuth2Authentication authentication, RequestUpdateCustomerPassword passwordDto);
}