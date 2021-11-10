package pl.recipes2pantry.recipe;

import lombok.Data;
import pl.recipes2pantry.product.ProductDto;

@Data
public class RecipeRequirementDto {
    private Long id;
    private Integer quantity;
    private ProductDto productDto;
}
