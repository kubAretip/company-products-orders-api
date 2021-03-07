package pl.kubaretip.cpo.api.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kubaretip.cpo.api.domain.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findBySupervisorIsNull();

    @Query("SELECT o FROM Order o")
    List<Order> findAllWithPageable(Pageable pageable);

}
