package pl.recipes2pantry.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.recipes2pantry.exceptions.EntityAlreadyExistsException;
import pl.recipes2pantry.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto createProduct(ProductDto productDto) {
        productRepository.findByNameAndWeight(productDto.getName(), productDto.getWeight())
                .ifPresent(product -> {
                    throw new EntityAlreadyExistsException("Product already exists");
                });
        Product product = productMapper.map(productDto);
        productRepository.save(product);
        return productMapper.map(product);
    }

    public ProductDto getProduct(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

    public void deleteProduct(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.deleteById(productId);
    }
}
