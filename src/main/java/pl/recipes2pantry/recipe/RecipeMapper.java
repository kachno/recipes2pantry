package pl.recipes2pantry.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RecipeMapper {
    private final RecipeRequirementMapper requirementMapper;

    public Recipe map(RecipeDto recipeDto) {
        final Recipe recipe = new Recipe();
        final Set<RecipeRequirement> requirements =
                toRequirementSet(recipeDto);
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setProductRequirements(requirements);
        return recipe;
    }

    public RecipeDto map(Recipe recipe) {
        final RecipeDto recipeDto = new RecipeDto();
        final Set<RecipeRequirementDto> requirementsDto =
                toRequirementDtoSet(recipe);
        recipeDto.setName(recipe.getName());
        recipeDto.setDescription(recipe.getDescription());
        recipeDto.setProductRequirementsDto(requirementsDto);
        return recipeDto;
    }

    private Set<RecipeRequirement> toRequirementSet(RecipeDto recipeDto) {
        return recipeDto.getProductRequirementsDto().stream()
                .map(requirementMapper::map)
                .collect(Collectors.toSet());
    }

    private Set<RecipeRequirementDto> toRequirementDtoSet(Recipe recipe) {
        return recipe.getProductRequirements().stream()
                .map(requirementMapper::map)
                .collect(Collectors.toSet());
    }

    public Recipe map(NewRecipeRequest newRecipeRequest) {
        final Recipe recipe = new Recipe();
        recipe.setName(newRecipeRequest.getName());
        recipe.setDescription(newRecipeRequest.getDescription());
        final Set<RecipeRequirement> requirements =
                toRequirementSetFromNewRecipe(newRecipeRequest);
        recipe.setProductRequirements(requirements);
        return recipe;
    }

    private Set<RecipeRequirement> toRequirementSetFromNewRecipe(NewRecipeRequest newRecipeRequest) {
        return newRecipeRequest.getRecipeRequirementRequests().stream()
                .map(requirementMapper::mapWithExistingProduct)
                .collect(Collectors.toSet());
    }
}
