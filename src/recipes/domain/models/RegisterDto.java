package recipes.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @Email(message = "email must be valid")
    @NotBlank(message = "email must not be blank")
    @NotNull
    private String email;

    @NotNull
    @NotBlank(message = "password must not be blank")
    @Size(min = 8,message = "password size must be at least 8 caracters")
    private String password;

    public boolean isValid() {
        if (!email.contains(".")) {
            return false;
        }
        return true;
    }
}
