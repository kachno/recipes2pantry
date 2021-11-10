package pl.recipes2pantry.pantry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {
    private List<ProductStockDto> productStocks;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ProductStockDto {
    private Integer onStock;
    private String productName;
    private Double productWeight;
    private Integer productCalories;
}
