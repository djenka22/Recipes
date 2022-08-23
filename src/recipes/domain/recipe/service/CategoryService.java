package recipes.domain.recipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.domain.recipe.entity.Category;
import recipes.domain.recipe.entity.Recipe;
import recipes.domain.recipe.models.RecipeDto;
import recipes.domain.recipe.repostory.CategoryRepo;
import recipes.infrastructure.mappers.RecipeMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private RecipeMapper recipeMapper;

    public Category findByName (String name) {
        return categoryRepo.findByName(name);
    }

    public List<RecipeDto> findAllByCategory(String category) {
        List<RecipeDto> recipeDtos = new ArrayList<>();
        List<Category> category1 = categoryRepo.findAllByNameIgnoreCase(category);
        if (category1.size() == 0) {
            return recipeDtos;
        }
        List<Recipe> recipes = new ArrayList<>();
        for (Category category2 : category1) {
            if (!recipes.containsAll(category2.getRecipes())) {
            recipes.addAll(category2.getRecipes());
            }
        }
        recipes = sortedRecipes(recipes);
        for (Recipe recipe : recipes) {
            recipeDtos.add(recipeMapper.recipeDtoCreateRecipe(recipe));
        }

        return recipeDtos;
    }

    private List<Recipe> sortedRecipes(List<Recipe> recipes) {
        for (int i = 0; i < recipes.size(); i++) {
            for (int j = i; j < recipes.size(); j++) {
                if (recipes.get(j).getDate().isAfter(recipes.get(i).getDate())){
                    Collections.swap(recipes, j, i);
                }
            }
        }
        return recipes;
    }

}
