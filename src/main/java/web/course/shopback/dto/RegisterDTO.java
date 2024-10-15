package web.course.shopback.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class RegisterDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String name;
    private String surname;
    private String phone;
    private String role;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Override
    public String toString() {
        return email + " " + password + " " + confirmPassword + " " + name + " " + surname + " " + phone + " " + role + " " + birthDate;
    }
}
