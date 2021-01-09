package com.mercadolivre.apimlv2.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthenticationManager authManager;

    private final TokenManager tokenManager;

    public AuthController(AuthenticationManager authManager, TokenManager tokenManager) {
        this.authManager = authManager;
        this.tokenManager = tokenManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthLoginResponse> authenticate(@RequestBody @Valid AuthLoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = request.build();

        try {   
            Authentication authentication = authManager.authenticate(authenticationToken);
            String jwt = tokenManager.generateToken(authentication);
            return ResponseEntity.ok(new AuthLoginResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
