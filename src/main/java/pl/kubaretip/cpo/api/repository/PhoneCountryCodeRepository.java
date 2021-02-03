package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kubaretip.cpo.api.domain.PhoneCountryCode;

public interface PhoneCountryCodeRepository extends JpaRepository<PhoneCountryCode, Long> {

    boolean existsByCountryIgnoreCase(String country);

    boolean existsByCodeIgnoreCase(String code);
}
