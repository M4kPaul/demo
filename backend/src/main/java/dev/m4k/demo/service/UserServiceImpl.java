package dev.m4k.demo.service;

import dev.m4k.demo.dto.RegisterRequest;
import dev.m4k.demo.exception.UserAlreadyExistAuthenticationException;
import dev.m4k.demo.model.User;
import dev.m4k.demo.model.Role;
import dev.m4k.demo.repo.RoleRepository;
import dev.m4k.demo.repo.UserRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  @Autowired
  private final UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional(value = "transactionManager")
  public User register(final RegisterRequest registerRequest) throws UserAlreadyExistAuthenticationException {
    if (registerRequest.getUserID() != null && userRepository.existsById(registerRequest.getUserID())) {
      throw new UserAlreadyExistAuthenticationException("User with UserID " + registerRequest.getUserID() + " already exist");
    } else if (userRepository.existsByUsername(registerRequest.getUsername())) {
      throw new UserAlreadyExistAuthenticationException("User with Username " + registerRequest.getUsername() + " already exist");
    }
    User user = buildUser(registerRequest);
    Date now = Calendar.getInstance().getTime();
    user.setCreatedDate(now);
    user = userRepository.save(user);
    userRepository.flush();
    return user;
  }

  private User buildUser(final RegisterRequest formDTO) {
    User user = new User();
    user.setUsername(formDTO.getUsername());
    user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
    final HashSet<Role> roles = new HashSet<>();
    roles.add(roleRepository.findByName(Role.ROLE_USER));
    user.setRoles(roles);
    user.setEnabled(true);
    return user;
  }

  @Override
  public User findUserByUsername(final String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public Optional<User> findUserById(Long id) {
    return userRepository.findById(id);
  }
}
