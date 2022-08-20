package recipes.domain.recipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipes.domain.recipe.entity.Recipe;
import recipes.domain.recipe.models.RecipeDto;

import recipes.domain.recipe.models.ReturnId;
import recipes.domain.recipe.repostory.RecipeRepo;
import recipes.infrastructure.exceptions.custom.RecipeNotFound;
import recipes.infrastructure.mappers.RecipeMapper;

@Service
@Transactional
public class RecipeService {
    @Autowired
    private RecipeRepo recipeRepo;
    @Autowired
    private RecipeMapper recipeMapper;

    public RecipeDto findById(Long id) {
       Recipe recipe = recipeRepo.findById(id).orElseThrow(RecipeNotFound::new);
       return recipeMapper.recipeDtoCreateRecipe(recipe);

    }

    public ReturnId createRecipe(RecipeDto recipeDto) {
         Recipe recipe = recipeMapper.recipeCreateDtoRecipe(recipeDto);
         recipe = recipeRepo.save(recipe);
         return new ReturnId(recipe.getId());
    }

    public void deleteById(Long id) {
        if (!recipeRepo.existsById(id)){
            throw new RecipeNotFound();
        }
        recipeRepo.deleteById(id);
    }
}
