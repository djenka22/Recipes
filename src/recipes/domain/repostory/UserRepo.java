package recipes.domain.repostory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.domain.entity.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findByEmail(String username);
}
