package recipes.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipes.domain.entity.Direction;
import recipes.domain.models.ReturnId;
import recipes.domain.entity.Ingredient;
import recipes.domain.entity.Recipe;
import recipes.domain.models.RecipeDto;

import recipes.domain.repostory.RecipeRepo;
import recipes.infrastructure.exceptions.custom.RecipeBadRequest;
import recipes.infrastructure.exceptions.custom.RecipeNotFound;
import recipes.infrastructure.mappers.RecipeMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeService {
    @Autowired
    private RecipeRepo recipeRepo;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RecipeMapper recipeMapper;

    public RecipeDto findById(Long id) {
       Recipe recipe = recipeRepo.findById(id).orElseThrow(
               () -> new RecipeNotFound("Recipe with such id does not exist")
       );
       return recipeMapper.recipeDtoCreateRecipe(recipe);

    }
    public Recipe findByIdRecipe(Long id) {
        return recipeRepo.findById(id).orElseThrow(() -> new RecipeNotFound("recipe not found"));
    }

    public ReturnId createRecipe(RecipeDto recipeDto) {
         Recipe recipe = recipeMapper.recipeCreateDtoRecipe(recipeDto);
         recipe = recipeRepo.save(recipe);
         return new ReturnId(recipe.getId());
    }

    public void deleteById(Long id) {
        if (!recipeRepo.existsById(id)){
            throw new RecipeNotFound("Recipe with id " + id + " does not exist!");
        }
        recipeRepo.deleteById(id);
    }

    public void updateRecipe(Long id, RecipeDto recipe) {
        if (!recipeRepo.existsById(id)) {
            throw new RecipeNotFound("Recipe with id " + id + " does not exist!");
        }
        Recipe fromDto = recipeMapper.recipeCreateDtoRecipe(recipe);
        Recipe recipeForUpdate = recipeRepo.findById(id).orElseThrow(()-> new RecipeNotFound("Recipe not found"));
        recipeForUpdate.setName(recipe.getName());
        recipeForUpdate.setDescription(recipe.getDescription());
        recipeForUpdate.setDate(LocalDateTime.now());
        recipeForUpdate.setCategory(categoryService.findByName(recipe.getCategory()));
        recipeForUpdate.setIngredients(fromDto.getIngredients());
        recipeForUpdate.setDirections(fromDto.getDirections());
        recipeRepo.save(recipeForUpdate);
    }

    public List<RecipeDto> findAllByCategoryOrName(String name, String category) {
        if (name != null && category != null) {
            throw new RecipeBadRequest();
        }
        if (name == null && category == null) {
            throw new RecipeBadRequest();
        }
        if (name != null) {
            return findAllByName(name);
        } else {
           // System.out.println(categoryService.findAllByCategory(category));

            return categoryService.findAllRecipesByName(category);
        }
    }

    private List<RecipeDto> findAllByName(String name) {
        List<RecipeDto> recipeDtos = new ArrayList<>();
        List<Recipe> recipes = recipeRepo.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
        if (recipes.size() == 0) {
            return recipeDtos;
        }
        for (Recipe recipe : recipes) {
            recipeDtos.add(recipeMapper.recipeDtoCreateRecipe(recipe));
        }
        return recipeDtos;
    }

}
