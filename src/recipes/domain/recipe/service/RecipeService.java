package recipes.domain.recipe.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipes.domain.recipe.entity.Category;
import recipes.domain.recipe.entity.Direction;
import recipes.domain.recipe.entity.Ingredient;
import recipes.domain.recipe.entity.Recipe;
import recipes.domain.recipe.models.RecipeDto;

import recipes.domain.recipe.models.ReturnId;
import recipes.domain.recipe.repostory.DirectionRepo;
import recipes.domain.recipe.repostory.IngredientRepo;
import recipes.domain.recipe.repostory.RecipeRepo;
import recipes.infrastructure.exceptions.custom.RecipeBadRequest;
import recipes.infrastructure.exceptions.custom.RecipeNotFound;
import recipes.infrastructure.mappers.RecipeMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class RecipeService {
    @Autowired
    private RecipeRepo recipeRepo;
    @Autowired
    private IngredientRepo ingredientRepo;
    @Autowired
    private DirectionRepo directionRepo;
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
        Recipe recipeForUpdate = recipeRepo.findById(id).orElseThrow(()-> new RecipeNotFound("Recipe not found"));
        log.info("recipe for update prosao");
        Recipe fromDto = recipeMapper.recipeCreateDtoRecipe(recipe);
        recipeForUpdate.setName(recipe.getName());
        recipeForUpdate.setDescription(recipe.getDescription());
        recipeForUpdate.setDate(LocalDateTime.now());
        log.info("recipe for update set name desc date");
        recipeForUpdate.setCategory(categoryService.findByName(recipe.getCategory()));
        log.info("recipe for update set category");
       /* recipeForUpdate.setIngredients(getIngredients(recipe));
        recipeForUpdate.setDirections(getDirections(recipe));
        */
        recipeForUpdate.setIngredients(fromDto.getIngredients());
        log.info("recipe for update set ingredients");
        recipeForUpdate.setDirections(fromDto.getDirections());
        log.info("recipe for update set directions");
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
            System.out.println(findAllByName(name));
            return findAllByName(name);
        } else {
            return categoryService.findAllByCategory(category);
        }
    }

   /* private List<RecipeDto> findAllByCategory(String category) {
        Category category1 = categoryService.findByName(category);
        List<RecipeDto> recipeDtos = new ArrayList<>();
        List<Recipe> recipes = recipeRepo.findAllByCategoryIgnoreCaseOrderByDateDesc(category1);
        if (recipes.size() == 0) {
            return recipeDtos;
        }
        for (Recipe recipe : recipes) {
            recipeDtos.add(recipeMapper.recipeDtoCreateRecipe(recipe));
        }
        return recipeDtos;
    }*/

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


    public Page<Recipe> findAllRecipes(Pageable pageable) {
       return recipeRepo.findAll(pageable);
    }
}
