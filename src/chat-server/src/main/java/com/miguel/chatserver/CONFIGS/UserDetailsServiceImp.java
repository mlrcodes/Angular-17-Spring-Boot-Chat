package com.miguel.chatserver.CONFIGS;

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

  private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImp.class);

  @Autowired
  private IUserService userService;

  @Override
  public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
    logger.debug("Entering in loadUserByUsername Method...");

    UserDetails userDetails = userService.findByPhoneNumber(phoneNumber);
    if (Objects.isNull(userDetails)) {
      logger.error("Username not found: " + phoneNumber);

      throw new UsernameNotFoundException("User not found");
    }

    return userDetails;
  }
}
