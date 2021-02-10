package pl.kubaretip.cpo.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table
@Entity
@Immutable
public class Status {

    @EmbeddedId
    private StatusPK id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

}
