package al.golocal.controller;

import al.golocal.dto.request.RefreshTokenRequest;
import al.golocal.entity.User;
import al.golocal.dto.request.LoginRequest;
import al.golocal.dto.request.SignupRequest;
import al.golocal.dto.response.LoginResponse;
import al.golocal.service.AuthenticationService;
import al.golocal.service.JwtService;
import al.golocal.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final UserService userService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody SignupRequest signupRequest) {
        User registeredUser = authenticationService.signup(signupRequest);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        // Authenticate the user
        User authenticatedUser = authenticationService.authenticate(loginRequest);

        // Generate the JWT access token
        String jwtToken = jwtService.generateToken(authenticatedUser);
        // Generate the refresh token (use the authenticatedUser for the refresh token)
        String jwtRefreshToken = jwtService.generateRefreshToken(authenticatedUser);

        // Prepare the response with both tokens
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setRefreshToken(jwtRefreshToken);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        // Validate the refresh token
        if (!jwtService.isTokenValid(refreshToken)) {
            throw new ExpiredJwtException(null,null,"Invalid refresh token");
        }

        // Extract the username (or subject) from the refresh token
        String username = jwtService.extractUsername(refreshToken);

        // Load the user details
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Generate a new access token
        String newAccessToken = jwtService.generateToken(userDetails);

        // Build the response
        LoginResponse response = new LoginResponse();
        response.setToken(newAccessToken);
        response.setExpiresIn(jwtService.getExpirationTime());
        response.setRefreshToken(refreshToken); // Optional: include the same refresh token

        return ResponseEntity.ok(response);
    }

}