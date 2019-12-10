package com.maxcoder.springsecurityjwt.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;

@Component(value = "UserDS")
@Service
public class UserDS implements UserDetailsService {

  private String u = "iths";
  private String p = "123"; // original

  public UserDS() throws InvalidKeySpecException, NoSuchAlgorithmException {
    p = "1000:10344c223835670c27f1ef8ddaad6556:fb396d0fd6d564b8b6d9da12038c19e131d6fe4e90c22b954408df2c909c429e43154cdc41fe22079e14e7080595bf240ea763fb78c4e377936681776714f0f3";
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username.equals(u)) {

      UserDetails userDetails = new User(u, p, new ArrayList<>());
      return userDetails;

    }

    return null;

  }
}