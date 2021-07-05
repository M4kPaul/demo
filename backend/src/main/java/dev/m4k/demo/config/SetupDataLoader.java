package dev.m4k.demo.config;

import dev.m4k.demo.model.Role;
import dev.m4k.demo.model.User;
import dev.m4k.demo.repo.RoleRepository;
import dev.m4k.demo.repo.UserRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private boolean alreadySetup = false;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    if (alreadySetup) {
      return;
    }
    Role userRole = createRoleIfNotFound(Role.ROLE_USER);
    Role adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);
    createUserIfNotFound("admin", Set.of(userRole, adminRole));
    alreadySetup = true;
  }

  @Transactional
  private final User createUserIfNotFound(final String username, Set<Role> roles) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      user = new User();
      user.setUsername("admin");
      user.setPassword(passwordEncoder.encode("admin@"));
      user.setRoles(roles);
      user.setEnabled(false); // ! disable for prod
      Date now = Calendar.getInstance().getTime();
      user.setCreatedDate(now);
      user = userRepository.save(user);
    }
    return user;
  }

  @Transactional
  private final Role createRoleIfNotFound(final String name) {
    Role role = roleRepository.findByName(name);
    if (role == null) {
      role = roleRepository.save(new Role(name));
    }
    return role;
  }
}
