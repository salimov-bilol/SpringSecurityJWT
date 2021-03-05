package com.harvard.demo.controller;

import com.harvard.demo.entity.AuthenticationRequest;
import com.harvard.demo.entity.AuthenticationResponse;
import com.harvard.demo.service.JWTService;
import com.harvard.demo.service.MyUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final MyUserDetailsService myUserDetailsService;

    public MyController(AuthenticationManager authenticationManager, JWTService jwtService, MyUserDetailsService myUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
    }

    @GetMapping("/")
    public String sayHello() {
        return "<h1>Hello World!</h1>";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            final String jwt = jwtService.generateToken(this.myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername()));
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } else {
            throw new Exception("Username or password not found.");
        }
    }
}
