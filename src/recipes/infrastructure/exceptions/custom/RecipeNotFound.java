package recipes.infrastructure.exceptions.custom;

public class RecipeNotFound extends RuntimeException {
    public RecipeNotFound(String message) {
        super(message);
    }
}
