package al.golocal.controller;

import al.golocal.dto.ApiResponse;
import al.golocal.dto.UserDto;
import al.golocal.service.UserService;
import al.golocal.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> allUsers() {
        List <UserDto> users = userService.allUsers();
        ApiResponse<List<UserDto>> apiResponse = new ApiResponse<List<UserDto>>(0,users,"");
        return ResponseEntity.ok(apiResponse);
    }
}