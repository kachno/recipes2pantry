package pl.recipes2pantry.pantry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.recipes2pantry.exceptions.EntityNotFoundException;
import pl.recipes2pantry.exceptions.EntityWrongValue;
import pl.recipes2pantry.product.Product;
import pl.recipes2pantry.product.ProductRepository;
import pl.recipes2pantry.stock.Stock;
import pl.recipes2pantry.stock.StockChangeRequest;
import pl.recipes2pantry.stock.StockRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class PantryStockModificationService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final PantryRepository pantryRepository;

    private void modifyStock(Stock stock, StockChangeRequest stockChangeRequest) {
        int newStockQuantity = stock.getCount() + stockChangeRequest.getQuantity();
        if (newStockQuantity < 0) {
            throw new EntityWrongValue("Quantity should be greater than 0");
        } else if (newStockQuantity == 0) {
            prepareStockForDelete(stock);
            stockRepository.deleteById(stock.getId());
        } else {
            stock.setCount(newStockQuantity);
        }
    }

    private void prepareStockForDelete(Stock stock) {
        stock.getPantry().getStockSet().remove(stock);
        stock.getProduct().getStocks().remove(stock);
        stock.setPantry(null);
        stock.setProduct(null);
    }

    private void addStock(StockChangeRequest stockChangeRequest, Pantry pantry, Product product) {
        Stock stock = new Stock();
        if (stockChangeRequest.getQuantity() < 0) {
            throw new EntityWrongValue("Quantity should be greater than 0");
        }
        stock.setCount(stockChangeRequest.getQuantity());
        stock.setPantry(pantry);
        pantry.getStockSet().add(stock);
        stock.setProduct(product);
        product.getStocks().add(stock);
    }

    public void modify(Long pantryId, Long productId, StockChangeRequest stockChangeRequest) {
        Pantry pantry = pantryRepository.findByIdWithStocks(pantryId)
                .orElseThrow(() -> new EntityNotFoundException("Pantry not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("product not found"));
        addOrModifyStock(productId, stockChangeRequest, pantry, product);
    }

    private void addOrModifyStock(Long productId, StockChangeRequest stockChangeRequest, Pantry pantry, Product product) {
        pantry.getStockSet().stream()
                .filter(stock -> stock.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(stock -> modifyStock(stock, stockChangeRequest),
                        () -> addStock(stockChangeRequest, pantry, product)
                );
    }
}
