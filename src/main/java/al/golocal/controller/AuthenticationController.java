package al.golocal.controller;

import al.golocal.dto.request.RefreshTokenRequest;
import al.golocal.dto.response.ApiResponse;
import al.golocal.dto.response.UserDto;
import al.golocal.entity.User;
import al.golocal.dto.request.LoginRequest;
import al.golocal.dto.request.SignupRequest;
import al.golocal.dto.response.LoginDto;
import al.golocal.service.AuthenticationService;
import al.golocal.service.JwtService;
import al.golocal.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody SignupRequest signupRequest) {
        User registeredUser = authenticationService.signup(signupRequest);
        return ResponseEntity.ok(new ApiResponse<>(0, userService.convertToDto(registeredUser), "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginDto>> authenticate(@RequestBody LoginRequest loginRequest) {
        // Authenticate the user
        User authenticatedUser = authenticationService.authenticate(loginRequest);

        // Generate the JWT access token
        String jwtToken = jwtService.generateToken(authenticatedUser);
        // Generate the refresh token (use the authenticatedUser for the refresh token)
        String jwtRefreshToken = jwtService.generateRefreshToken(authenticatedUser);

        // Prepare the response with both tokens
        LoginDto loginDto = new LoginDto();
        loginDto.setAccessToken(jwtToken);
        loginDto.setExpiresIn(jwtService.getExpirationTime());
        loginDto.setRefreshToken(jwtRefreshToken);
        loginDto.setRefreshExpiresIn(jwtService.getRefreshExpirationTime());
        loginDto.setUser(userService.convertToDto(authenticatedUser));

        ApiResponse apiResponse = new ApiResponse(0, loginDto,"User logged in successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginDto>> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
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
        LoginDto response = new LoginDto();
        response.setAccessToken(newAccessToken);
        response.setExpiresIn(jwtService.getExpirationTime());
        response.setRefreshToken(refreshToken); // Optional: include the same refresh token
        response.setRefreshExpiresIn(jwtService.getRefreshExpirationTime());

        return ResponseEntity.ok(new ApiResponse<LoginDto>(0, response, "Access token refreshed successfully"));
    }

}