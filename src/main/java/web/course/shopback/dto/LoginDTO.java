package web.course.shopback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String email;
    private String password;

    @Override
    public String toString() {
        return email + " " + password;
    }
}
