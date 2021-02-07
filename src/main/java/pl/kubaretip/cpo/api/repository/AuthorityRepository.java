package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kubaretip.cpo.api.domain.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

    @Query(value =
            "SELECT * from authority where upper(name)=upper(:name) OR upper(view_name)=upper(:name)",
            nativeQuery = true)
    Optional<Authority> findByNameOrViewNameIgnoreCase(String name);

}
