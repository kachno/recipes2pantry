package pl.recipes2pantry.product;

import lombok.*;
import pl.recipes2pantry.stock.Stock;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = "stocks")
@ToString(exclude = "stocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;
    private String name;
    private Double weight;
    private Integer kcal;
    @OneToMany
    @JoinColumn(name = "product_id")
    private Set<Stock> stocks = new HashSet<>();
}
