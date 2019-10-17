package cu.vlired.submod.repository;

import cu.vlired.submod.model.Document;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luizo
 */
public interface DocumentRepository extends JpaRepository<Document, Long>{

}
