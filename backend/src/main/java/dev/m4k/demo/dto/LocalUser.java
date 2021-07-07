package dev.m4k.demo.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import dev.m4k.demo.util.GeneralUtils;

public class LocalUser extends User {

  private final static long serialVersionUID = -3626163969604747239L;

  private Map<String, Object> attributes;
  private dev.m4k.demo.model.User user;

  public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
      final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final dev.m4k.demo.model.User user) {
    super(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.user = user;
  }

  public static LocalUser create(dev.m4k.demo.model.User user, Map<String, Object> attributes) {
    LocalUser localUser = new LocalUser(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()),
        user);
    localUser.setAttributes(attributes);
    return localUser;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public dev.m4k.demo.model.User getUser() {
    return user;
  }
}

