package web.course.shopback.repositories;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;
import web.course.shopback.models.User;

@Repository
public interface UserRepository extends CosmosRepository<User, String> {
}
