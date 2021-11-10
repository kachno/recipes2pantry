package pl.recipes2pantry.pantry;

import lombok.Data;
import pl.recipes2pantry.stock.StockDto;

import java.util.Set;

@Data
public class PantryDto {
    private Long id;
    private String name;
    private Set<StockDto> stockSet;
}
