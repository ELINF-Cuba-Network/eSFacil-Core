/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.repository;

import cu.vlired.submod.model.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author luizo
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

        Optional<Role>  findByName(String Role_Type);
        List<Role>      findAll();

}
