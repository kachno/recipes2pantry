package pl.recipes2pantry.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductMapper {
    private final ProductRepository productRepository;

    public Product map(ProductDto productDto) {
        final Product product = new Product();
        product.setName(productDto.getName());
        product.setWeight(productDto.getWeight());
        product.setKcal(productDto.getKcal());
        return product;
    }

    public ProductDto map(Product product) {
        final ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setWeight(product.getWeight());
        productDto.setKcal(product.getKcal());
        return productDto;
    }

    public ProductDto mapByProductId(Long productId) {
        return map(productRepository.getById(productId));
    }
}
