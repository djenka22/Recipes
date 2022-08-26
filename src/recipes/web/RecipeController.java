package recipes.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import recipes.domain.entity.Recipe;
import recipes.domain.models.RecipeDto;
import recipes.domain.models.ReturnId;
import recipes.domain.service.RecipeService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/recipe")
@Slf4j
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
        log.info("get recipe before findbyid");
        RecipeDto recipe = recipeService.findById(id);
        log.info("get recipe after find by id");
        return ResponseEntity.ok(recipe);
    }

    @PostMapping("/new")
    public ResponseEntity<ReturnId> createRecipe(@Valid @RequestBody RecipeDto recipe) {
        ReturnId createdRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.ok().body(createdRecipe);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Recipe recipe = recipeService.findByIdRecipe(id);
        if (!recipe.getUser().getEmail().equals(userDetails.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        recipeService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id,
                                          @Valid @RequestBody RecipeDto recipe,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        Recipe recipeToUpdate = recipeService.findByIdRecipe(id);
        if (!recipeToUpdate.getUser().getEmail().equals(userDetails.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        recipeService.updateRecipe(id, recipe);
        log.info("update");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public List<RecipeDto> findAllByCategoryOrName(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false,  name = "category") String category) {
        log.info("search");
        return recipeService.findAllByCategoryOrName(name, category);

    }


}
