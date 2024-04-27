package com.sergiosantiago.conectados.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergiosantiago.conectados.config.JwtUtils;
import com.sergiosantiago.conectados.dtos.UserDTO;
import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.models.auth.JwtRequest;
import com.sergiosantiago.conectados.models.auth.JwtResponse;
import com.sergiosantiago.conectados.services.UserService;

@RestController()
@RequestMapping(value = "/authenticate", consumes = { "application/json" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private UserService userDetailsService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtTokenUtil;
    private ModelMapper modelMapper;

    public AuthController(UserService userDetailsService, AuthenticationManager authenticationManager,
            JwtUtils jwtTokenUtil, ModelMapper modelMapper) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/register", consumes = { "application/json" })
    public ResponseEntity<JwtResponse> registerUser(@RequestBody UserDTO registrationRequest) {
        return userDetailsService.registerUser(registrationRequest);
    }

    @PostMapping(value = "/login", consumes = { "application/json" })
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authenticationRequest) {
        ResponseEntity<JwtResponse> res = null;
        try {
            final UserDetails userDetails = userDetailsService.loginUser(authenticationRequest);
            authenticate(authenticationRequest.getEmail(),
                    authenticationRequest.getPassword());
            User user = userDetailsService.findByEmail(userDetails.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);

            res = ResponseEntity.ok(new JwtResponse(token, user.getDTO()));
        } catch (Exception e) {
            res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JwtResponse("Invalid credentials"));
        }
        return res;
    }

    @GetMapping(value = "/all")
    public List<UserDTO> listUsers() {
        return userDetailsService.findAll().stream().map(e -> modelMapper.map(e, UserDTO.class)).toList();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
