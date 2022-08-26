package recipes.domain.repostory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.domain.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepo extends CrudRepository<Category, Long> {
    Category findByName(String name);

    List<Category> findAllByNameIgnoreCase(String category);
}
