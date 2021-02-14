package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kubaretip.cpo.api.domain.Status;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findByNameIgnoreCaseAndLocaleIgnoreCase(String name, String locale);

}
