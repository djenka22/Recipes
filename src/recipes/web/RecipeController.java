package recipes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.domain.recipe.models.RecipeDto;
import recipes.domain.recipe.models.ReturnId;
import recipes.domain.recipe.service.RecipeService;
import javax.validation.Valid;

@RestController
@RequestMapping("api/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
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
}
