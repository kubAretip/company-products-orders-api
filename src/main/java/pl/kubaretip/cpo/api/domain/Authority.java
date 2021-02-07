package pl.kubaretip.cpo.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Immutable
public class Authority {

    @Id
    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "view_name", length = 45, nullable = false, unique = true)
    private String viewName;

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

}
