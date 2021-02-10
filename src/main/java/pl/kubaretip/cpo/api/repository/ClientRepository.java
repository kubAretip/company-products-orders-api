package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kubaretip.cpo.api.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByCompanyNameIgnoreCase(String companyName);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhoneNumberIgnoreCase(String phoneNumber);

}
