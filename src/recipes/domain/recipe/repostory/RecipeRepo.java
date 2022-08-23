package recipes.domain.recipe.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.domain.recipe.entity.Category;
import recipes.domain.recipe.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe,Long> {


    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);

   // List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(Category category);


    //List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
}
