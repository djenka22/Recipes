package recipes.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import recipes.infrastructure.exceptions.custom.RecipeBadRequest;
import recipes.infrastructure.exceptions.custom.RecipeNotFound;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecipeNotFound.class)
    public ResponseEntity<?> OnRecipeNotFound (RecipeNotFound ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RecipeBadRequest.class)
    public ResponseEntity<?> OnRecipeBadRequest () {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
