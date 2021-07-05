package dev.m4k.demo.util;

import dev.m4k.demo.dto.LocalUser;
import dev.m4k.demo.dto.UserInfo;
import dev.m4k.demo.model.Role;
import dev.m4k.demo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class GeneralUtils {

  public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    return authorities;
  }

  public static UserInfo buildUserInfo(LocalUser localUser) {
    List<String> roles = localUser.getAuthorities().stream().map(item -> item.getAuthority()).collect(
        Collectors.toList());
    User user = localUser.getUser();
    return new UserInfo(user.getId().toString(), user.getUsername(), roles);
  }
}
