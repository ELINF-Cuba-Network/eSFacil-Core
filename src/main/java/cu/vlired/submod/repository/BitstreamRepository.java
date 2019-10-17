/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.vlired.submod.repository;

import cu.vlired.submod.model.Bitstream;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author luizo
 */
public interface BitstreamRepository extends JpaRepository<Bitstream, Long>{
    
}
