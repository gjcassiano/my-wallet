package com.wallet.controllers;

import com.wallet.dto.LoginDTO;
import com.wallet.dto.UserDTO;
import com.wallet.entities.User;
import com.wallet.mapper.UserMapper;
import com.wallet.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */


@Api(tags = "User Controller")
@Lazy
@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
@Profile("user")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/signin")
    @ApiOperation(value = "Signin")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.signin(loginDTO.getKey(), loginDTO.getPassword()));
    }

    @ApiOperation(value = "Signup")
    @PostMapping("/signup")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDTO){
        User userToBeCreate = UserMapper.INSTANCE.toEntity(userDTO);
        return ResponseEntity.ok(userService.createAndGetToken(userToBeCreate));
    }


    @ApiOperation(value = "Get User by Key")
    @GetMapping("/{key}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<UserDTO> getUser(@PathVariable("key") String key){
        return ResponseEntity.ok(UserMapper.INSTANCE.toDTO(userService.getUserByKey(key)));
    }

    @ApiOperation(value = "Get Logged User")
    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<UserDTO> getLoggedUser() {
        return ResponseEntity.ok(UserMapper.INSTANCE.toDTO(userService.getLoggedUser()));
    }

    @ApiOperation(value = "RefreshToken")
    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<String> refresh(HttpServletRequest req) {
        return ResponseEntity.ok(userService.refresh(req.getRemoteUser()));
    }

}
