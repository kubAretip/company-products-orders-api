package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kubaretip.cpo.api.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.id=:id AND c.deleted = false")
    Optional<Category> findById(@Param("id") long categoryId);

    @Query("SELECT " +
            "CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Category c " +
            "WHERE upper(c.name) = upper(:name) AND c.deleted = false")
    boolean existsByName(@Param("name") String categoryName);

    @Query("SELECT c FROM Category c WHERE UPPER(c.name) = UPPER(:name) and c.deleted = false")
    Optional<Category> findByName(@Param("name") String categoryName);

    @Query("SELECT c FROM Category c WHERE c.deleted = false")
    List<Category> findAll();

}
