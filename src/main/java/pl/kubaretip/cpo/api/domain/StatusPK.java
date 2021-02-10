package pl.kubaretip.cpo.api.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StatusPK implements Serializable {

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false, length = 11)
    private String locale;

}
