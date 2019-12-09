package com.maxcoder.springsecurityjwt;

import com.maxcoder.springsecurityjwt.security.Crypt;
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
  @Qualifier("MyUserDetailsService")
  private UserDetailsService userDetailsService;


  @RequestMapping({"/hello"})
  public String firstPage() {
    return "ITHS Controller";
  }


//  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//    String username = "";
//    String password = "";
//
//    try {
//      username = authenticationRequest.getUsername();
//      password = authenticationRequest.getPassword();
//
//      System.out.println("Username: " + username);
//      System.out.println("Password: " + password);
//
//      authenticationManager.authenticate(
//              new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
//      );
//    } catch (BadCredentialsException e) {
//      System.out.println("111");
//      throw new Exception("Incorrect username or password", e);
//    }
//    final UserDetails userDetails = userDetailsService
//            .loadUserByUsername(username);
//
//
//    System.out.println("UserDetails: " + userDetails.toString());
//
//    System.out.println("UserDetailsName: " + userDetails.getUsername());
//    System.out.println("UserDetailsPassword: " + userDetails.getPassword());
//
//    boolean validUser = Crypt.validatePassword(password, userDetails.getPassword());
//
//    System.out.println(validUser);
//
//    if (validUser) {
//      final String jwt = jwtTokenUtil.generateToken(userDetails);
//      return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }
//    return ResponseEntity.notFound().build();
//  }


  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
      );
    } catch (BadCredentialsException e) {
      System.out.println("111");
      throw new Exception("Incorrect username or password", e);
    }
    final UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.getUsername());
    final String jwt = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

}
