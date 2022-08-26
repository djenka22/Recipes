package recipes.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    private void onCreate() {
        this.date = LocalDateTime.now(ZoneId.of("Z"));
    }
    @PreUpdate
    private void onUpdate() {
        this.date = LocalDateTime.now(ZoneId.of("Z"));
    }


}
