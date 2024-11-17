package al.golocal.service;

import al.golocal.entity.User;
import al.golocal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import al.golocal.dto.response.UserDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database
        al.golocal.entity.User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Convert your User entity into Spring Security's UserDetails
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
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
}
