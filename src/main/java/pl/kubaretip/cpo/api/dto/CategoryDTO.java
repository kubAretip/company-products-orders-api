package pl.kubaretip.cpo.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO(Long id) {
        this.id = id;
    }

    public CategoryDTO(String name) {
        this.name = name;
    }
}
