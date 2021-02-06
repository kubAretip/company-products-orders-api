package pl.kubaretip.cpo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kubaretip.cpo.api.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

    @Query(
            value = "SELECT * from user where upper(first_name)= upper(:firstName) " +
                    "and upper(last_name)=upper(:lastName) order by username desc limit 1",
            nativeQuery = true)
    Optional<User> findLatestUsernameForFirstNameAndLastName(@Param("firstName") String firstName,
                                                             @Param("lastName") String lastName);
    Optional<User> findByUsernameIgnoreCase(String username);

}
