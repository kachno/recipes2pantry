package pl.recipes2pantry.pantry;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.recipes2pantry.recipe.RecipeRequirementDto;
import pl.recipes2pantry.recipe.RecipeService;
import pl.recipes2pantry.stock.StockChangeRequest;

import javax.validation.Valid;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pantries")
public class PantryController {
    private final PantryStockModificationService pantryStockModificationService;
    private final PantryService pantryService;
    private final RecipeService recipeService;

    @Operation(summary = "Get all content from pantry")
    @GetMapping("/{id}/content")
    public ContentDto getProductStocks(@PathVariable Long id) {
        return pantryService.getAllContent(id);
    }

    @Operation(summary = "Get a list of missing pantry products needed to complete the recipe")
    @GetMapping("/{pantryId}/recipes/{recipeName}")
    public Set<RecipeRequirementDto> getMissingProductsList(@PathVariable Long pantryId, @PathVariable String recipeName) {
        return recipeService.listRequiredProductsForRecipe(pantryId, recipeName);
    }

    @Operation(summary = "Add a new pantry")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PantryDto addPantry(@RequestBody @Valid PantryDto pantryDto) {
        return pantryService.createPantry(pantryDto);
    }

    @Operation(summary = "Change products quantity in pantry")
    @PutMapping("/{pantryId}/products/{productId}")
    public void modifyPantryProductStock(
            @PathVariable Long pantryId,
            @PathVariable Long productId,
            @RequestBody StockChangeRequest stockChangeRequest) {
        pantryStockModificationService.modify(pantryId, productId, stockChangeRequest);
    }

    @Operation(summary = "Delete the pantry")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePantry(@PathVariable("id") Long pantryId) {
        pantryService.deletePantry(pantryId);
    }
}
