package recipes.infrastructure.mappers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import recipes.domain.recipe.entity.Category;
import recipes.domain.recipe.entity.Direction;
import recipes.domain.recipe.entity.Ingredient;
import recipes.domain.recipe.entity.Recipe;
import recipes.domain.recipe.models.RecipeDto;
import recipes.domain.recipe.repostory.IngredientRepo;
import recipes.domain.recipe.service.CategoryService;
import recipes.domain.recipe.service.RecipeService;
import recipes.infrastructure.exceptions.custom.RecipeNotFound;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RecipeMapper {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RecipeService recipeService;
    public Recipe recipeCreateDtoRecipe(RecipeDto recipeDto) {
        if (recipeDto == null) {
            return null;
        }
        Recipe recipe = new Recipe();
        recipe.setDate(LocalDateTime.now(ZoneId.of("Z")));
        //recipe.setCreatedOrUpdatedAt(LocalDateTime.now(ZoneId.of("Z")));
        Optional<Category> category = Optional.ofNullable(
                categoryService.findByName(recipeDto.getCategory())
        );

        recipe.setCategory(category.orElse(
                new Category(recipeDto.getCategory())
                ));
        //recipe.setCategory(categoryService.findByName(recipeDto.getCategory()));
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());

        List<Ingredient> ingredients = recipeDto.getIngredients().stream().map(Ingredient::new).collect(Collectors.toList());

        List<Direction> directions = recipeDto.getDirections().stream().map(Direction::new).collect(Collectors.toList());

        recipe.setDirections(directions);
        recipe.setIngredients(ingredients);
        return recipe;
    }

    public RecipeDto recipeDtoCreateRecipe(Recipe recipe) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setDateTime(recipe.getDate());
        recipeDto.setCategory(recipe.getCategory().getName());
        recipeDto.setName(recipe.getName());
        recipeDto.setDescription(recipe.getDescription());
        List<String> ingredients = new ArrayList<>();
        for (Ingredient ing : recipe.getIngredients()) {
            log.info("eeevoo mee");
            ingredients.add(ing.getValue());
        }
        List<String> directions = new ArrayList<>();
        for (Direction dir : recipe.getDirections()) {
            directions.add(dir.getValue());
        }
        recipeDto.setIngredients(ingredients);
        recipeDto.setDirections(directions);
        return recipeDto;
    }



}
