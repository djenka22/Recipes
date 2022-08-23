package recipes.domain.recipe.repostory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.domain.recipe.entity.Direction;

import java.util.List;

@Repository
public interface DirectionRepo extends CrudRepository<Direction, Long> {

}
