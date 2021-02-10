package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kubaretip.cpo.api.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
