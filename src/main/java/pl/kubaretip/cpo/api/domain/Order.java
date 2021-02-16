package pl.kubaretip.cpo.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "client_order")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marketer_user_id", nullable = false)
    private User marketer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_user_id")
    private User supervisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id", nullable = false)
    private Address deliveryAddress;

    @Column(name = "additional_information", length = 2000)
    private String additionalInformation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", targetEntity = OrderStatus.class, cascade = {CascadeType.PERSIST})
    private Set<OrderStatus> orderStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", targetEntity = OrderProduct.class, cascade = {CascadeType.PERSIST})
    private Set<OrderProduct> orderProducts;

}
