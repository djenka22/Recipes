package recipes.domain.repostory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.domain.entity.Ingredient;

@Repository
public interface IngredientRepo extends CrudRepository<Ingredient,Long> {
}
