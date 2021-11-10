package pl.recipes2pantry.product;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get all products from db")
    @GetMapping
    public List<ProductDto> getProducts() {
        return productService.getProducts();
    }

    @Operation(summary = "Get one product from db")
    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable("id") Long productId) {
        return productService.getProduct(productId);
    }

    @Operation(summary = "Create a new product")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDto addProduct(@RequestBody @Valid ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @Operation(summary = "Delete the product from db")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
    }
}
