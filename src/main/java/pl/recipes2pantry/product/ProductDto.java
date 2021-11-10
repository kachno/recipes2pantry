package pl.recipes2pantry.product;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull(message = "Weight cannot be empty value")
    @DecimalMin("0")
    private Double weight;
    @NotNull(message = "Calories cannot be empty value")
    @Min(0)
    private Integer kcal;
}
