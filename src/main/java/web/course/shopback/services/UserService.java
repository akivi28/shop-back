package web.course.shopback.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.course.shopback.dto.LoginDTO;
import web.course.shopback.dto.RegisterDTO;
import web.course.shopback.models.auth.User;
import web.course.shopback.models.auth.UserSecurity;
import web.course.shopback.repositories.RoleRepository;
import web.course.shopback.repositories.UserRepository;
import web.course.shopback.repositories.UserSecurityRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserSecurityRepository userSecurityRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserSecurityRepository userSecurityRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userSecurityRepository = userSecurityRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public User registerUser(RegisterDTO registerDTO) {
        User existingUser = userRepository.findByEmail(registerDTO.getEmail());
        if (existingUser != null) {
            List<UserSecurity> userSecurityList = userSecurityRepository.findByUser_UserId(existingUser.getUserId());
            if (userSecurityList.size() > 1) {
                throw new IllegalArgumentException("Такий користувач вже існує");
            }
            UserSecurity userSecurity = userSecurityList.get(0);
            if (userSecurity.getRole().getName().equals(registerDTO.getRole())) {
                throw new IllegalArgumentException("Помилка реєстрації: користувач з такою роллю вже існує.");
            }
            String plainPassword = registerDTO.getPassword();
            String storedHashedPassword = userSecurity.getPasswordHash();
            if(passwordEncoder.matches(plainPassword, storedHashedPassword)){
                throw new IllegalArgumentException("Паролі для різних ролей не можуть співпадати");
            }
            UserSecurity newUserSecurity = new UserSecurity();
            newUserSecurity.setUser(existingUser);
            newUserSecurity.setRole(roleRepository.findByName(registerDTO.getRole()));
            newUserSecurity.setLogin(registerDTO.getEmail());
            newUserSecurity.setPasswordHash(passwordEncoder.encode(registerDTO.getPassword()));
            userSecurityRepository.save(newUserSecurity);
            return newUserSecurity.getUser();
        }

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Паролі не співпадають");
        }

        if (registerDTO.getBirthDate().after(new Date())) {
            throw new IllegalArgumentException("Дата народження не може бути в майбутньому");
        }

        User newUser = new User();
        newUser.setEmail(registerDTO.getEmail());
        newUser.setFirstName(registerDTO.getName());
        newUser.setLastName(registerDTO.getSurname());
        newUser.setPhoneNumber(registerDTO.getPhone());
        LocalDate birthDate = registerDTO.getBirthDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        newUser.setBirthDate(birthDate);

        newUser.setRegistrationDate(LocalDate.now());

        User savedUser = userRepository.save(newUser);

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUser(savedUser);
        userSecurity.setRole(roleRepository.findByName(registerDTO.getRole()));
        userSecurity.setLogin(registerDTO.getEmail());
        userSecurity.setPasswordHash(passwordEncoder.encode(registerDTO.getPassword()));
        userSecurityRepository.save(userSecurity);

        return savedUser;
    }

    public User loginUser(LoginDTO loginDTO){
        User existingUser = userRepository.findByEmail(loginDTO.getEmail());
        if (existingUser == null) {
            throw new IllegalArgumentException("Користувача з таким email не існує");
        }
        List<UserSecurity> userSecurityList = userSecurityRepository.findByUser_UserId(existingUser.getUserId());
        for (UserSecurity userSecurity : userSecurityList) {
            String plainPassword = loginDTO.getPassword();
            String storedHashedPassword = userSecurity.getPasswordHash();
            if(passwordEncoder.matches(plainPassword, storedHashedPassword)){
                return userSecurity.getUser();
            }
        }
        throw new IllegalArgumentException("Не вірний пароль");
    }




}
