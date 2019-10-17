/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.repository;

import cu.vlired.submod.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 *
 * @author luizo
 */
public interface UserRepository extends JpaRepository<User, Long>{
    
    List<User> findAll();
    List<User> findByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String username,
            String firstName,
            String lastName,
            String email
    );

    // Same up but when JPQL
    @Query( "SELECT u   FROM User u " +
            "WHERE      LOWER(u.username)   LIKE LOWER(CONCAT('%', :pattern, '%'))" +
            "OR         LOWER(u.firstName)  LIKE LOWER(CONCAT('%', :pattern, '%'))" +
            "OR         LOWER(u.lastName)   LIKE LOWER(CONCAT('%', :pattern, '%'))" +
            "OR         LOWER(u.email)      LIKE LOWER(CONCAT('%', :pattern, '%'))"
    )
            
    List<User> paginateWithSearch(@Param("pattern") String pattern, Pageable pageable);
     
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    
}

