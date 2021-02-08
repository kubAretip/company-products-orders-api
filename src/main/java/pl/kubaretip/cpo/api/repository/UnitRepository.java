package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kubaretip.cpo.api.domain.Unit;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query("SELECT u FROM Unit u WHERE u.id = :id AND u.deleted = false")
    Optional<Unit> findById(@Param("id") Long unitId);

    @Query("SELECT " +
            "CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM Unit u " +
            "WHERE upper(u.name) = upper(:name) AND u.deleted = false")
    boolean existsByName(@Param("name") String name);

    @Query("SELECT " +
            "CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM Unit u " +
            "WHERE upper(u.symbol) = upper(:symbol) AND u.deleted = false")
    boolean existsBySymbol(@Param("symbol") String symbol);

    @Query("SELECT u FROM Unit u WHERE upper(u.name) = upper(:name) AND u.deleted = false")
    Optional<Unit> findByName(@Param("name") String name);

    @Query("SELECT u FROM Unit u WHERE upper(u.symbol) = upper(:symbol) AND u.deleted = false")
    Optional<Unit> findBySymbol(@Param("symbol") String symbol);

    @Query("SELECT " +
            "CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM Unit u " +
            "WHERE u.id = :id AND u.deleted = false")
    boolean existsById(@Param("id") Long id);

}
