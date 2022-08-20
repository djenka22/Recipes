package recipes.infrastructure.exceptions.custom;

public class RecipeNotFound extends RuntimeException {
    public RecipeNotFound() {
        super("Recipe not found!");
    }
}
