package dev.m4k.demo.service;

import dev.m4k.demo.dto.RegisterRequest;
import dev.m4k.demo.exception.UserAlreadyExistAuthenticationException;
import dev.m4k.demo.model.User;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

  @Transactional(value = "transactionManager")
  User register(RegisterRequest registerRequest) throws UserAlreadyExistAuthenticationException;

  User findUserByUsername(String username);

  Optional<User> findUserById(Long id);
}
