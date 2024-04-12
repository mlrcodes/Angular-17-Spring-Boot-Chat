package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.MODELS.Role;

public interface IRoleService {
  public Role findByName(String name);
}
