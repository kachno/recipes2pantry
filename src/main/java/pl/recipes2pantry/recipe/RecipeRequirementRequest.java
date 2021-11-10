package pl.recipes2pantry.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RecipeRequirementRequest {
    @NotNull
    @Min(1)
    private Integer quantity;
    @NotNull
    private Long productId;
}