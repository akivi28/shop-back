package web.course.shopback.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.course.shopback.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
