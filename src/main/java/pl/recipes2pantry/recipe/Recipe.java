package pl.recipes2pantry.recipe;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(exclude = "productRequirements")
@ToString(exclude = "productRequirements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    private String name;
    private String description;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "recipe_id")
    private Set<RecipeRequirement> productRequirements;
}
