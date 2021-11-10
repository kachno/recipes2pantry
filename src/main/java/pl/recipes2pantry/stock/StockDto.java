package pl.recipes2pantry.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.recipes2pantry.product.ProductDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
    private Long id;
    private ProductDto product;
    private Integer count;
}
