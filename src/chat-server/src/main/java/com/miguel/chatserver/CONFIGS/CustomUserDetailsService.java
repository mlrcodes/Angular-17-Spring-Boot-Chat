package com.miguel.chatserver.CONFIGS;

import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.MODELS.UserPrincipal;
import com.miguel.chatserver.SERVICES.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private IUserService userService;

  @Override
  public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
    User user = this.userService.findByPhoneNumber(phoneNumber);

    if (Objects.isNull(user)) {
      throw new ExceptionObjectNotFound("User Not Found");
    }

    return new UserPrincipal(user);
  }
}
