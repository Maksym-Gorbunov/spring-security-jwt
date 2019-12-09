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

@Component(value="MyUserDetailsService")
@Service
public class MyUserDetailsService implements UserDetailsService {

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        try {
//            username = "iths";
//            String passwordOriginal = "123";
//
//            String passwordHashed = Crypt.generatePasswordHash(passwordOriginal);
//
//
//            System.out.println("passwordHardcodad: " + passwordOriginal);
//            System.out.println("passwordHardcodadHashed: " + passwordHashed);
//
//
//            UserDetails userDetails = new User(username, passwordHashed, new ArrayList<>());
//
//            return userDetails;
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            UserDetails userDetails = new User("iths", "123", new ArrayList<>());
            return userDetails;
    }
}