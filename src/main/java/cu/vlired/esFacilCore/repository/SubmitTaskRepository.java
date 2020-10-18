package cu.vlired.esFacilCore.repository;

import cu.vlired.esFacilCore.model.SubmitTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubmitTaskRepository
    extends JpaRepository<SubmitTask, UUID>  {
}
