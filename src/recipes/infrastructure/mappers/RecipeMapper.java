package recipes.infrastructure.mappers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import recipes.domain.entity.*;
import recipes.domain.models.RecipeDto;
import recipes.domain.service.CategoryService;
import recipes.domain.service.UserService;
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
    private UserService userService;
    public Recipe recipeCreateDtoRecipe(RecipeDto recipeDto) {
        if (recipeDto == null) {
            log.error("recipe dto is null");
            return null;
        }
        Recipe recipe = new Recipe();
        User user = userService.findByEmail(getCurrentUser().getUsername());
        log.info("user {} found",user.getEmail());
        recipe.setUser(user);
        recipe.setDate(LocalDateTime.now(ZoneId.of("Z")));
        Optional<Category> category = Optional.ofNullable(
                categoryService.findByName(recipeDto.getCategory())
        );

        recipe.setCategory(category.orElse(
                new Category(recipeDto.getCategory())
                ));
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

        recipeDto.setDateTime(recipe.getDate().toString());
        recipeDto.setCategory(recipe.getCategory().getName());
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

    private UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
