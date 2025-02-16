package al.golocal.service;

import al.golocal.entity.User;
import al.golocal.exception.AuthenticationFailedException;
import al.golocal.mapper.GenericMapper;
import al.golocal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import al.golocal.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;

    private final GenericMapper genericMapper;

    private final UserRepository userRepository;

    public List<UserDto> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return genericMapper.toDtoList(users, UserDto.class);
    }

    public User getUserByUsername(String username){
        User user = userRepository.findByEmailOrUsername(username,username)
                .orElseThrow(() -> new AuthenticationFailedException("User not found"));
        return user;
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database
        User user = userRepository.findByEmailOrUsername(username,username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Convert your User entity into Spring Security's UserDetails
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().getName().name()) // Assuming roles are enums or strings
                .build();
    }

    public List<User> findUsersByCity(String city) {
        return userRepository.findByAddressCity(city);
    }

    public List<User> findUsersByCountry(String country) {
        return userRepository.findByAddressCountry(country);
    }

    public List<User> findUsersByZipCode(String zipCode) {
        return userRepository.findByAddressZipCode(zipCode);
    }

    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User u = (User)authentication.getPrincipal();
            return u.getUserId();
        }
        return null;
    }
}
