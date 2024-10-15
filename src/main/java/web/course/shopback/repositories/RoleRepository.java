package web.course.shopback.repositories;

import org.springframework.data.repository.CrudRepository;
import web.course.shopback.models.auth.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
