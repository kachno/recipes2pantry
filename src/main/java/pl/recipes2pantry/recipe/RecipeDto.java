package pl.recipes2pantry.recipe;

import lombok.Data;

import javax.validation.Valid;
import java.util.Set;

@Data
public class RecipeDto {
    private String name;
    private String description;
    @Valid
    private Set<RecipeRequirementDto> productRequirementsDto;
}
