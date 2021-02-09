package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kubaretip.cpo.api.domain.CountryCallingCode;

public interface CountryCallingCodeRepository extends JpaRepository<CountryCallingCode, Long> {

    boolean existsByCountryIgnoreCase(String country);

    boolean existsByCodeIgnoreCase(String code);
}
