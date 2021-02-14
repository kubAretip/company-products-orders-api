package pl.kubaretip.cpo.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryCallingCode {

    @Id
    @Column(length = 64, nullable = false, unique = true)
    private String country;

    @Column(length = 7, nullable = false)
    private String code;

}
