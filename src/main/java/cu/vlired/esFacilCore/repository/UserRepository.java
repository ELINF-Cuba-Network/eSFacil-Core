package cu.vlired.esFacilCore.repository;

import cu.vlired.esFacilCore.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository
        extends JpaRepository<User, UUID>, CustomUserRepository {
    
    List<User> findAll();

    @Query( "SELECT u   FROM User u " +
            "WHERE      LOWER(u.username)   LIKE LOWER(CONCAT('%', :pattern, '%'))" +
            "OR         LOWER(u.firstname)  LIKE LOWER(CONCAT('%', :pattern, '%'))" +
            "OR         LOWER(u.lastname)   LIKE LOWER(CONCAT('%', :pattern, '%'))" +
            "OR         LOWER(u.email)      LIKE LOWER(CONCAT('%', :pattern, '%'))"
    )
    List<User> paginateAndSearch(@Param("pattern") String pattern, Pageable pageable);
     
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
}

