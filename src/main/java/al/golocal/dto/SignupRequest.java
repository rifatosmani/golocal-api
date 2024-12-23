package al.golocal.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String username;

    private int roleId;

}