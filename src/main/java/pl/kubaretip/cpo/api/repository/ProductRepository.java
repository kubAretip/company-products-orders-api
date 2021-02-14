package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kubaretip.cpo.api.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT " +
            "CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Product p " +
            "WHERE upper(p.name) = upper(:name) AND p.deleted = false")
    boolean existsByName(@Param("name") String productName);

    @Query("SELECT p FROM Product p WHERE upper(p.name) = upper(:name) AND p.deleted = false")
    Optional<Product> findByName(@Param("name") String productName);

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.deleted = false")
    Optional<Product> findById(@Param("id") Long productId);

    @Query("SELECT p FROM Product p WHERE p.deleted = false")
    List<Product> findAll();

    @Query("SELECT " +
            "CASE WHEN COUNT (p) > 0 THEN true ELSE false END " +
            "FROM Product p " +
            "WHERE p.id = :id AND p.deleted = false")
    boolean existsById(@Param("id") Long productId);

    @Query("SELECT p FROM Product p WHERE p.id in :ids AND p.deleted = false")
    List<Product> findByIdIn(@Param("ids") List<Long> productsIds);

    @Query("SELECT " +
            "CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Product p " +
            "WHERE p.id in :ids AND p.deleted = false")
    boolean existsByIdIn(@Param("ids") List<Long> productIds);

}
