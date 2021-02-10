package pl.kubaretip.cpo.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_status")
@Entity
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "status", referencedColumnName = "name", insertable = false, updatable = false),
            @JoinColumn(name = "status_locale", referencedColumnName = "locale", insertable = false, updatable = false)
    })
    private Status status;

    @Column(name = "status_date", nullable = false)
    private OffsetDateTime statusDate = OffsetDateTime.now();

}
