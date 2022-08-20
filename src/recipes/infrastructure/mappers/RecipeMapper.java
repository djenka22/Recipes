package recipes.infrastructure.mappers;


import org.springframework.stereotype.Component;
import recipes.domain.recipe.entity.Direction;
import recipes.domain.recipe.entity.Ingredient;
import recipes.domain.recipe.entity.Recipe;
import recipes.domain.recipe.models.RecipeDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {
    public Recipe recipeCreateDtoRecipe(RecipeDto recipeDto) {
        if (recipeDto == null) {
            return null;
        }
        Recipe recipe = new Recipe();

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
        recipeDto.setName(recipe.getName());
        recipeDto.setDescription(recipe.getDescription());
        List<String> ingredients = new ArrayList<>();
        for (Ingredient ing : recipe.getIngredients()) {
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
