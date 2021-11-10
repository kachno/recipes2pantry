package pl.recipes2pantry.stock;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.recipes2pantry.pantry.Pantry;
import pl.recipes2pantry.product.Product;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Pantry pantry;
    private Integer count;
}
