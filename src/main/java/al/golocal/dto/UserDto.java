package al.golocal.dto;

import al.golocal.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UserDto {
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}
