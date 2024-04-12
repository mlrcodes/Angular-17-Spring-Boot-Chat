package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.MODELS.Role;
import com.miguel.chatserver.REPOSITORIES.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImpRoleService implements IRoleService {

  @Autowired
  private IRoleRepository roleRepository;
  @Override
  public Role findByName(String name) {
    return roleRepository.findByName(name).orElse(null);
  }
}
