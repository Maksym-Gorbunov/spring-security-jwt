package com.maxcoder.springsecurityjwt;

import com.maxcoder.springsecurityjwt.model.AuthenticationRequest;
import com.maxcoder.springsecurityjwt.model.AuthenticationResponse;
import com.maxcoder.springsecurityjwt.services.MyUserDetailsService;
import com.maxcoder.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private MyUserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;


  @RequestMapping("/hello")
  public String hello(){
    return "HELLO CONTROLLER";
  }

  // Get username and password, return JWT (Json Web Token)
  @RequestMapping(value = "/authentiacate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
    try{
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
      );
    }catch (BadCredentialsException e){
      throw new Exception("Incorrect username or password", e);
    }
    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String jwt = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }



}
