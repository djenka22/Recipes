package recipes.domain.recipe.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    //@Size(min = 1)
    @NotEmpty
    private List<@NotBlank String> ingredients;
    //@Size(min = 1)
    @NotEmpty
    private List<@NotBlank String> directions;
}
