package recipes.domain.recipe.repostory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.domain.recipe.entity.Recipe;

@Repository
public interface RecipeRepo extends CrudRepository<Recipe,Long> {


}
