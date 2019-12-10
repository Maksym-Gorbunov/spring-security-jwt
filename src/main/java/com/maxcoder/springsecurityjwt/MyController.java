package com.maxcoder.springsecurityjwt;

import com.maxcoder.springsecurityjwt.security.HashEncoder;
import com.maxcoder.springsecurityjwt.security.models.AuthenticationRequest;
import com.maxcoder.springsecurityjwt.security.models.AuthenticationResponse;
import com.maxcoder.springsecurityjwt.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MyController {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtUtil jwtTokenUtil;
  @Autowired
  @Qualifier("UserDS")
  private UserDetailsService userDetailsService;


  @RequestMapping({"/hello"})
  public String firstPage() {
    return "ITHS Controller";
  }


  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    final String username = authenticationRequest.getUsername();
    final String password = authenticationRequest.getPassword();
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect username or password", e);
    }
    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    final String jwt = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

}
