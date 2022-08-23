package recipes.domain.recipe.repostory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.domain.recipe.entity.Ingredient;

import java.util.List;

@Repository
public interface IngredientRepo extends CrudRepository<Ingredient,Long> { 
}
