package cu.vlired.esFacilCore.repository;

import cu.vlired.esFacilCore.model.Rejection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RejectionRepository extends JpaRepository<Rejection, UUID> {
}
