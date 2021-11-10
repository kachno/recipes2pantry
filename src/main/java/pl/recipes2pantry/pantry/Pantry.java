package pl.recipes2pantry.pantry;

import lombok.*;
import pl.recipes2pantry.stock.Stock;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = "stockSet")
@ToString(exclude = "stockSet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pantries")
public class Pantry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pantry_id")
    private Set<Stock> stockSet = new HashSet<>();
}
