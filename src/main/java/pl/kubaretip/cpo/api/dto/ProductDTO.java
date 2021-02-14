package pl.kubaretip.cpo.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private CategoryDTO category;
    private UnitDTO unit;

    public ProductDTO(Long id) {
        this.id = id;
    }

    public ProductDTO(String name, CategoryDTO category, UnitDTO unit) {
        this.name = name;
        this.category = category;
        this.unit = unit;
    }

}
