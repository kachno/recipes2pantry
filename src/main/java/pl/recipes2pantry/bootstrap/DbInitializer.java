package pl.recipes2pantry.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.recipes2pantry.pantry.Pantry;
import pl.recipes2pantry.pantry.PantryRepository;
import pl.recipes2pantry.pantry.PantryStockModificationService;
import pl.recipes2pantry.product.Product;
import pl.recipes2pantry.product.ProductRepository;
import pl.recipes2pantry.recipe.Recipe;
import pl.recipes2pantry.recipe.RecipeRepository;
import pl.recipes2pantry.recipe.RecipeRequirement;
import pl.recipes2pantry.stock.StockChangeRequest;

import java.util.Set;

@RequiredArgsConstructor
@Component
@Profile("dev")
public class DbInitializer implements CommandLineRunner {
    private final PantryRepository pantryRepository;
    private final ProductRepository productRepository;
    private final PantryStockModificationService pantryStockModificationService;
    private final RecipeRepository recipeRepository;

    @Override
    public void run(String... args) throws Exception {
        Pantry pantry = pantryRepository.save(
                Pantry.builder()
                        .name("Kachno")
                        .build()
        );
        Product tomato = productRepository.save(
                Product.builder()
                        .name("tomato")
                        .weight(200.0)
                        .kcal(75)
                        .build()
        );
        Product egg = productRepository.save(
                Product.builder()
                        .name("egg")
                        .weight(50.0)
                        .kcal(34)
                        .build()
        );
        Product butter = productRepository.save(
                Product.builder()
                        .name("butter")
                        .weight(200.0)
                        .kcal(746)
                        .build()
        );
        Product bread = productRepository.save(
                Product.builder()
                        .name("bread")
                        .weight(250.0)
                        .kcal(500)
                        .build()
        );
        pantryStockModificationService.modify(pantry.getId(), tomato.getId(), new StockChangeRequest(4));
        pantryStockModificationService.modify(pantry.getId(), egg.getId(), new StockChangeRequest(6));

        RecipeRequirement eggRequirement = RecipeRequirement.builder()
                .product(egg)
                .quantity(2)
                .build();
        RecipeRequirement butterRequirement = RecipeRequirement.builder()
                .product(butter)
                .quantity(1)
                .build();
        RecipeRequirement breadRequirement = RecipeRequirement.builder()
                .product(bread)
                .quantity(1)
                .build();

        recipeRepository.save(
                Recipe.builder()
                        .productRequirements(Set.of(eggRequirement, butterRequirement))
                        .name("scrambled eggs")
                        .description("break the eggs and put them in the pan with the melted butter...")
                        .build()
        );

        recipeRepository.save(
                Recipe.builder()
                        .productRequirements(Set.of(eggRequirement, butterRequirement, breadRequirement))
                        .name("egg sandwich")
                        .description("slice the bread and the egg, then brush the butter and add the egg...")
                        .build()
        );
    }
}
