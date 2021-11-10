package pl.recipes2pantry.pantry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.recipes2pantry.stock.Stock;
import pl.recipes2pantry.stock.StockDto;
import pl.recipes2pantry.stock.StockMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PantryMapper {
    private final StockMapper stockMapper;

    public Pantry map(PantryDto pantryDto) {
        final Pantry pantry = new Pantry();
        pantry.setName(pantryDto.getName());
        return pantry;
    }

    public PantryDto map(Pantry pantry) {
        final PantryDto pantryDto = new PantryDto();
        pantryDto.setId(pantry.getId());
        pantryDto.setName(pantry.getName());
        final Set<StockDto> stockSetDto =
                pantry.getStockSet().stream()
                        .map(stockMapper::map)
                        .collect(Collectors.toSet());
        pantryDto.setStockSet(stockSetDto);
        return pantryDto;
    }

    public ContentDto mapToContent(Pantry pantry) {
        final ContentDto contentDto = new ContentDto();
        final Set<Stock> stockSet = pantry.getStockSet();
        final List<ProductStockDto> productStocks = toProductStockDtoList(stockSet);
        contentDto.setProductStocks(productStocks);
        return contentDto;
    }

    private List<ProductStockDto> toProductStockDtoList(Set<Stock> stockSet) {
        return stockSet.stream()
                .map(this::getProductStockDto)
                .collect(Collectors.toList());
    }

    private ProductStockDto getProductStockDto(Stock stock) {
        return new ProductStockDto(
                stock.getCount(),
                stock.getProduct().getName(),
                stock.getProduct().getWeight(),
                stock.getProduct().getKcal());
    }
}
