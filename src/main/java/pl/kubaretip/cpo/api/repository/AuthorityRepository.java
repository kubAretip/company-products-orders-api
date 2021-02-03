package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kubaretip.cpo.api.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
