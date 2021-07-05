package dev.m4k.demo.controller;

import dev.m4k.demo.config.AppProperties.Auth;
import dev.m4k.demo.dto.ApiResponse;
import dev.m4k.demo.dto.JwtAuthenticationResponse;
import dev.m4k.demo.dto.LocalUser;
import dev.m4k.demo.dto.LoginRequest;
import dev.m4k.demo.dto.RegisterRequest;
import dev.m4k.demo.exception.UserAlreadyExistAuthenticationException;
import dev.m4k.demo.security.jwt.TokenProvider;
import dev.m4k.demo.service.UserService;
import dev.m4k.demo.util.GeneralUtils;
import io.jsonwebtoken.Jwt;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserService userService;

  @Autowired
  TokenProvider tokenProvider;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.createToken(authentication);
    LocalUser localUser = (LocalUser) authentication.getPrincipal();

    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)));
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
    try {
      userService.register(registerRequest);
    } catch (UserAlreadyExistAuthenticationException e) {
      log.error("Exception occured", e);
      return new ResponseEntity<>(new ApiResponse(false, "User already exists!"), HttpStatus.BAD_REQUEST);
    }

    return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
  }
}
