package recipes.domain.recipe.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private LocalDateTime date;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "recipe_id")
    private List<Ingredient> ingredients;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "recipe_id")
    private List<Direction> directions;

    @PrePersist
    private void onCreate() {
        this.date = LocalDateTime.now(ZoneId.of("Z"));
    }
    @PreUpdate
    private void onUpdate() {
        this.date = LocalDateTime.now(ZoneId.of("Z"));
    }

}
