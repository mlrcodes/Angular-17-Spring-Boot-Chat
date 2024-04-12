package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
  public Optional<Role> findByName(String role);
}
