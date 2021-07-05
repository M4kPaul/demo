package dev.m4k.demo.repo;

import dev.m4k.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

  Boolean existsByUsername(String username);
}
