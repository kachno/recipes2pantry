package pl.recipes2pantry.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.recipes2pantry.exceptions.EntityAlreadyExistsException;
import pl.recipes2pantry.exceptions.EntityNotFoundException;
import pl.recipes2pantry.pantry.PantryDto;
import pl.recipes2pantry.pantry.PantryService;
import pl.recipes2pantry.product.ProductMapper;
import pl.recipes2pantry.product.ProductRepository;
import pl.recipes2pantry.stock.StockDto;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;
    private final RecipeMapper recipeMapper;
    private final ProductMapper productMapper;
    private final PantryService pantryService;

    public List<RecipeDto> getRecipes() {
        return recipeRepository.findAll().stream()
                .map(recipeMapper::map)
                .collect(Collectors.toList());
    }

    public RecipeDto createRecipe(NewRecipeRequest newRecipeRequest) {
        recipeRepository.findById(newRecipeRequest.getName())
                .ifPresent(recipe -> {
                    throw new EntityAlreadyExistsException("Recipe already exists");
                });
        boolean allProductIdsMatched = newRecipeRequest.getRecipeRequirementRequests().stream()
                .map(RecipeRequirementRequest::getProductId)
                .allMatch(productRepository::existsById);
        if (!allProductIdsMatched) {
            throw new EntityNotFoundException("Missing products from recipe in DB");
        }
        Recipe recipe = recipeMapper.map(newRecipeRequest);
        return recipeMapper.map(recipeRepository.save(recipe));
    }

    public RecipeDto getRecipe(String recipeName) {
        return recipeRepository.findById(recipeName)
                .map(recipeMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Recipe doesn't exists"));
    }

    public void modifyRecipe(String recipeName, String description) {
        Recipe recipe = recipeRepository.findById(recipeName)
                .orElseThrow(() -> new EntityNotFoundException("Recipe doesn't exists"));
        recipe.setDescription(description);
    }

    public void deleteRecipe(String recipeName) {
        recipeRepository.findById(recipeName)
                .orElseThrow(() -> new EntityNotFoundException("Recipe doesn't exists"));
        recipeRepository.deleteById(recipeName);
    }

    public Set<RecipeRequirementDto> listRequiredProductsForRecipe(Long pantryId, String recipeName) {
        PantryDto pantryDto = pantryService.getPantry(pantryId);
        RecipeDto recipeDto = this.getRecipe(recipeName);

        Set<RecipeRequirementDto> missingRecipeRequests = new HashSet<>();
        Set<StockDto> pantryStock = pantryDto.getStockSet();
        Set<RecipeRequirementDto> requiredProductsDto = recipeDto.getProductRequirementsDto();
        Map<Long, Integer> stockMap = new HashMap<>();
        Map<Long, Integer> recipeMap = new HashMap<>();

        pantryStock
                .forEach(stockDto -> {
                            stockMap.putIfAbsent(stockDto.getProduct().getId(), stockDto.getCount());
                        }
                );

        requiredProductsDto
                .forEach(recipeReq -> {
                            recipeMap.putIfAbsent(recipeReq.getProductDto().getId(), recipeReq.getQuantity());
                        }
                );

        for (Long RecipeProdId : recipeMap.keySet()) {
            if (stockMap.containsKey(RecipeProdId)) { //product in pantry
                if (stockMap.get(RecipeProdId) < recipeMap.get(RecipeProdId)) {
                    addMissingProductQuantityInPantry(missingRecipeRequests, stockMap, recipeMap, RecipeProdId);
                }
            } else { //not in pantry
                addMissingProductQuantityNotInPantry(missingRecipeRequests, recipeMap, RecipeProdId);
            }
        }
        return missingRecipeRequests;
    }

    private void addMissingProductQuantityNotInPantry(Set<RecipeRequirementDto> missingRecipeRequests, Map<Long, Integer> recipeMap, Long RecipeProdId) {
        RecipeRequirementDto recipeRequirementDto = new RecipeRequirementDto();
        recipeRequirementDto.setQuantity(recipeMap.get(RecipeProdId));
        recipeRequirementDto.setProductDto(productMapper.mapByProductId(RecipeProdId));
        missingRecipeRequests.add(recipeRequirementDto);
    }

    private void addMissingProductQuantityInPantry(Set<RecipeRequirementDto> missingRecipeRequests, Map<Long, Integer> stockMap, Map<Long, Integer> recipeMap, Long RecipeProdId) {
        Integer reqQuantity = recipeMap.get(RecipeProdId) - stockMap.get(RecipeProdId);
        RecipeRequirementDto recipeRequirementDto = new RecipeRequirementDto();
        recipeRequirementDto.setQuantity(reqQuantity);
        recipeRequirementDto.setProductDto(productMapper.mapByProductId(RecipeProdId));
        missingRecipeRequests.add(recipeRequirementDto);
    }
}
