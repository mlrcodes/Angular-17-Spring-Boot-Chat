package com.miguel.chatserver.CONFIGS;

import com.miguel.chatserver.SERVICES.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
  private IUserService userService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
    UserDetails userDetails = userService.findByPhoneNumber(phoneNumber);
    if (Objects.isNull(userDetails)) {
      throw new UsernameNotFoundException("User not found");
    }
    else return userDetails;
  }
}
