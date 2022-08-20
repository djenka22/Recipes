package recipes.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import recipes.infrastructure.exceptions.custom.RecipeNotFound;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecipeNotFound.class)
    public ResponseEntity<?> OnRecipeNotFound () {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
