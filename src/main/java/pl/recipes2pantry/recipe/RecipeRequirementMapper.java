package pl.recipes2pantry.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.recipes2pantry.product.ProductMapper;
import pl.recipes2pantry.product.ProductRepository;

@RequiredArgsConstructor
@Component
public class RecipeRequirementMapper {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public RecipeRequirement map(RecipeRequirementDto requirementDto) {
        final RecipeRequirement requirement = new RecipeRequirement();
        requirement.setId(requirementDto.getId());
        requirement.setQuantity(requirementDto.getQuantity());
        requirement.setProduct(productMapper.map(requirementDto.getProductDto()));
        return requirement;
    }

    public RecipeRequirementDto map(RecipeRequirement requirement) {
        final RecipeRequirementDto requirementDto = new RecipeRequirementDto();
        requirementDto.setId(requirement.getId());
        requirementDto.setQuantity(requirement.getQuantity());
        requirementDto.setProductDto(productMapper.map(requirement.getProduct()));
        return requirementDto;
    }

    public RecipeRequirement mapWithExistingProduct(RecipeRequirementRequest recipeRequirementRequest) {
        final RecipeRequirement requirement = new RecipeRequirement();
        requirement.setQuantity(recipeRequirementRequest.getQuantity());
        requirement.setProduct(productRepository.getById(recipeRequirementRequest.getProductId()));
        return requirement;
    }

}
