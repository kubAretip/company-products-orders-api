package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kubaretip.cpo.api.domain.UserActivationReport;

import java.util.Optional;

public interface UserActivationReportRepository extends JpaRepository<UserActivationReport, Long> {

    @Query("SELECT r FROM UserActivationReport r where r.user.id = :userId")
    Optional<UserActivationReport> findByUserId(Long userId);


}
