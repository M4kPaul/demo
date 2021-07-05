package dev.m4k.demo.service;

import dev.m4k.demo.dto.LocalUser;
import dev.m4k.demo.exception.ResourceNotFoundException;
import dev.m4k.demo.model.User;
import dev.m4k.demo.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("localUserDetailService")
public class LocalUserDetailService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  @Transactional
  public LocalUser loadUserByUsername(final String username) throws UsernameNotFoundException {
    User user = userService.findUserByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User " + username + " was not found in the database");
    }
    return createLocalUser(user);
  }

  @Transactional
  public LocalUser loadUserById(Long id) {
    User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    return createLocalUser(user);
  }

  private LocalUser createLocalUser(User user) {
    return new LocalUser(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, GeneralUtils
        .buildSimpleGrantedAuthorities(user.getRoles()), user);
  }
}
