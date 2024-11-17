package al.golocal.service;

import al.golocal.dto.request.LoginRequest;
import al.golocal.dto.request.SignupRequest;
import al.golocal.entity.Role;
import al.golocal.entity.User;
import al.golocal.exception.AuthenticationFailedException;
import al.golocal.repository.RoleRepository;
import al.golocal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public User signup(SignupRequest input) {

        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        Role role = roleRepository.findById(input.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid role ID: " + input.getRoleId()));
        user.setRole(role);

        return userRepository.save(user);
    }

    public User authenticate(LoginRequest input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new AuthenticationFailedException("User not found"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new AuthenticationFailedException("Invalid password");
        }


        return user;
    }
}