package cu.vlired.esFacilCore.repository;

import cu.vlired.esFacilCore.model.Bitstream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BitstreamRepository extends JpaRepository<Bitstream, UUID>{
    
}
