package recipes.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    @NotBlank
    private String category;

    @JsonProperty("date")
    private String dateTime;

    @NotEmpty
    private List<@NotBlank String> ingredients;
    //@Size(min = 1)
    @NotEmpty
    private List<@NotBlank String> directions;
}
