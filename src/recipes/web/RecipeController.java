package recipes.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import recipes.domain.recipe.entity.Recipe;
import recipes.domain.recipe.models.RecipeDto;
import recipes.domain.recipe.models.ReturnId;
import recipes.domain.recipe.service.RecipeService;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/recipe")
@Slf4j
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("{id}")
    public ResponseEntity<RecipeDto> findById(@PathVariable Long id) {
        RecipeDto recipe = recipeService.findById(id);
        return ResponseEntity.ok(recipe);
    }

    @PostMapping("/new")
    public ResponseEntity<ReturnId> createRecipe(@Valid @RequestBody RecipeDto recipe) {
        ReturnId createdRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.ok().body(createdRecipe);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        recipeService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeDto recipe) {
        recipeService.updateRecipe(id, recipe);
        log.info("update");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Page<Recipe>> findAllRecipes(Pageable pageable) {
       return ResponseEntity.ok(recipeService.findAllRecipes(pageable));
    }

    @GetMapping("/search")
    public List<RecipeDto> findAllByCategoryOrName(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        log.info("search");
        return recipeService.findAllByCategoryOrName(name, category);

    }

}
