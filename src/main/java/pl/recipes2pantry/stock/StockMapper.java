package pl.recipes2pantry.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.recipes2pantry.product.Product;
import pl.recipes2pantry.product.ProductDto;
import pl.recipes2pantry.product.ProductMapper;

@RequiredArgsConstructor
@Component
public class StockMapper {
    private final ProductMapper productMapper;

    public Stock map(StockDto stockDto) {
        final Stock stock = new Stock();
        final Product product = productMapper.map(stockDto.getProduct());
        stock.setId(stockDto.getId());
        stock.setProduct(product);
        stock.setCount(stockDto.getCount());
        return stock;
    }

    public StockDto map(Stock stock) {
        final StockDto stockDto = new StockDto();
        final ProductDto productDto = productMapper.map(stock.getProduct());
        stockDto.setId(stock.getId());
        stockDto.setProduct(productDto);
        stockDto.setCount(stock.getCount());
        return stockDto;
    }
}
