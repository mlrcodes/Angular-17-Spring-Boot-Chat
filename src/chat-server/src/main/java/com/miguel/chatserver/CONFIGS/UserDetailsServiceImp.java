package com.miguel.chatserver.CONFIGS;

import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.MODELS.UserPrincipal;
import com.miguel.chatserver.SERVICES.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
  private IUserService userService;

  @Override
  public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
    User user = userService.findByPhoneNumber(phoneNumber);

    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("User not found");
    }

    return new UserPrincipal(user);
  }
}
