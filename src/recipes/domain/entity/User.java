package recipes.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import recipes.RecipesApplication;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private Set<Recipe> recipes;


    public static class Builder {
        private String email;
        private String password;

        public Builder() {
        }

        public Builder createEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder createPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(
                    null,
                    email,
                    password,
                    new HashSet<>(List.of())
            );
        }
    }

}