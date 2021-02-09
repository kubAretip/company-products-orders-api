package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kubaretip.cpo.api.domain.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

    @Query("SELECT a from Authority a where upper(a.name)=upper(:name) OR upper(a.viewName)=upper(:name)")
    Optional<Authority> findByNameOrViewNameIgnoreCase(@Param("name") String nameOrViewName);

}
