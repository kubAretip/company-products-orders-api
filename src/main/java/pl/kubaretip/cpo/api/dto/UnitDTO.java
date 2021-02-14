package pl.kubaretip.cpo.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDTO {

    private Long id;
    private String name;
    private String symbol;

    public UnitDTO(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public UnitDTO(Long id) {
        this.id = id;
    }
}
