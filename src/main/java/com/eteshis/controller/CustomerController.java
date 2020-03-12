package com.eteshis.controller;


import com.eteshis.constraints.MessageConstraints;
import com.eteshis.domain.User;
import com.eteshis.dto.RequestRegisterDto;
import com.eteshis.dto.RequestUpdateCustomerPassword;
import com.eteshis.dto.UserDTO;
import com.eteshis.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {


    @Autowired
    private UserServiceImpl userService;

    @ApiOperation(
            value = "Register a new Customer",
            notes = "Reference to login.html. After registration completed, verification code sent to email. to customer login  he/she must first confirm email."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = MessageConstraints.MAIL_DUPLICATED
                            + "\n |" + MessageConstraints.PHONE_NUMBER_DUPLICATED
                            + "\n |" + MessageConstraints.USERNAME_DUPLICATED
                    )
            }
    )
    @PostMapping(value = "/register")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> registerCustomer(@RequestBody @Valid UserDTO dto) {


        userService.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @ApiOperation(
            value = "Change password of customer.",
            notes = "Reference to profile edit page of all customer types")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message =
                            MessageConstraints.USER_TOKEN_NOT_VALID
                                    + "\n| " + MessageConstraints.USER_NOT_FOUND
                    )
            }
    )
    @PostMapping(value = "/change-password")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid RequestUpdateCustomerPassword dto, OAuth2Authentication authentication) {

        userService.changePassword(authentication, dto);

    }


}
