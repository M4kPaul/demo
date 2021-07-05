package dev.m4k.demo.controller;

import dev.m4k.demo.config.CurrentUser;
import dev.m4k.demo.dto.LocalUser;
import dev.m4k.demo.dto.RegisterRequest;
import dev.m4k.demo.service.UserServiceImpl;
import dev.m4k.demo.util.GeneralUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

  @GetMapping("/user/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
    return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
  }
}
