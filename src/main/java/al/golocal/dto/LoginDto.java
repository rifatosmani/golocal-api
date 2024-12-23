package al.golocal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String accessToken;

    private String refreshToken;

    private long expiresIn;

    private long refreshExpiresIn;

    private UserDto user;

}