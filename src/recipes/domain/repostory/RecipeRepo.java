package recipes.domain.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.domain.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe,Long> {


    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);

   // List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(Category category);


    //List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
}
