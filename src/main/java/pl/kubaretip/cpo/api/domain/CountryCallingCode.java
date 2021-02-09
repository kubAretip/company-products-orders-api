package pl.kubaretip.cpo.api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table
@Entity
@Getter
@Setter
public class CountryCallingCode implements Serializable {

    @Id
    @Column(length = 64, nullable = false, unique = true)
    private String country;

    @Column(length = 7, nullable = false)
    private String code;

}
