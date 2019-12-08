package com.maxcoder.springsecurityjwt;

import com.maxcoder.springsecurityjwt.models.AuthenticationRequest;
import com.maxcoder.springsecurityjwt.models.AuthenticationResponse;
import com.maxcoder.springsecurityjwt.util.JwtUtil;
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

import javax.validation.Valid;

@RestController
class MyController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
  @Qualifier("MyUserDetailsService")
	private UserDetailsService userDetailsService;

	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

    System.out.println(authenticationRequest.toString());

    System.out.println(authenticationRequest.getPassword());
    System.out.println(authenticationRequest.getUsername());
    try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
      System.out.println("111");
		}
		catch (BadCredentialsException e) {
      System.out.println("000");
			throw new Exception("Incorrect username or password", e);
		}
    System.out.println("222");

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
    System.out.println("333");
		final String jwt = jwtTokenUtil.generateToken(userDetails);
    System.out.println("444");
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
