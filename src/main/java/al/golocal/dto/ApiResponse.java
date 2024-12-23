package al.golocal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private int status;
    private T data;
    private String message;

    // Constructors
    public ApiResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    // Getters and Setters
}
