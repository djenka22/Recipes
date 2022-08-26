package recipes.domain.repostory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.domain.entity.Direction;

@Repository
public interface DirectionRepo extends CrudRepository<Direction, Long> {

}
