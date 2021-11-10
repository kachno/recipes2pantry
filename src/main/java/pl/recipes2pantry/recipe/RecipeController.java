package pl.recipes2pantry.recipe;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @Operation(summary = "Get all recipes from db")
    @GetMapping
    public List<RecipeDto> getRecipes() {
        return recipeService.getRecipes();
    }

    @Operation(summary = "Get one recipe by name")
    @GetMapping("/{recipeName}")
    public RecipeDto getRecipe(@PathVariable String recipeName) {
        return recipeService.getRecipe(recipeName);
    }

    @Operation(summary = "Create a new recipe")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RecipeDto createRecipe(@RequestBody @Valid NewRecipeRequest newRecipeRequest) {
        return recipeService.createRecipe(newRecipeRequest);
    }

    @Operation(summary = "Change the recipe description")
    @PutMapping("/{recipeName}")
    public void modifyRecipe(@PathVariable String recipeName, @RequestBody String description) {
        recipeService.modifyRecipe(recipeName, description);
    }

    @Operation(summary = "Delete the recipe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{recipeName}")
    public void deleteRecipe(@PathVariable String recipeName) {
        recipeService.deleteRecipe(recipeName);
    }
}
