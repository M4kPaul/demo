package dev.m4k.demo.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistAuthenticationException extends AuthenticationException {

  private static final long serialVersionUID = 4609872160861504706L;

  public UserAlreadyExistAuthenticationException(final String msg) {
    super(msg);
  }
}
