package web.course.shopback.repositories;

import org.springframework.data.repository.CrudRepository;
import web.course.shopback.models.auth.UserSecurity;

import java.util.List;

public interface UserSecurityRepository extends CrudRepository<UserSecurity, Integer> {
    List<UserSecurity> findByUser_UserId(Integer userId);
}
