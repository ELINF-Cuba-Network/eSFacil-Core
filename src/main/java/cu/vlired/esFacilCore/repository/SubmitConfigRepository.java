package cu.vlired.esFacilCore.repository;

import cu.vlired.esFacilCore.model.SubmitConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubmitConfigRepository
    extends JpaRepository<SubmitConfig, UUID>  {
}
