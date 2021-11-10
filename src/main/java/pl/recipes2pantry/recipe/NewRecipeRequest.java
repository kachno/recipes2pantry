package pl.recipes2pantry.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewRecipeRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Valid
    @NotNull
    private Set<RecipeRequirementRequest> recipeRequirementRequests;

    @AssertTrue
    public boolean recipeRequirementRequests() {
        return recipeRequirementRequests.stream()
                .map(RecipeRequirementRequest::getQuantity)
                .allMatch(quantity -> quantity != null && quantity > 0);
    }
}

