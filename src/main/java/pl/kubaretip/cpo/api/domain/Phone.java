package pl.kubaretip.cpo.api.domain;

import javax.persistence.*;

@Embeddable
public class Phone {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_number_country_code", referencedColumnName = "code")
    private PhoneCountryCode phoneCountryCode;

    @Column(name = "phone_number", length = 13)
    private Long phoneNumber;

}
