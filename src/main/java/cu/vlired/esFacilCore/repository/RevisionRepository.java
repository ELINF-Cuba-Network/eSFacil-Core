package cu.vlired.esFacilCore.repository;

import cu.vlired.esFacilCore.model.Revision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RevisionRepository extends JpaRepository<Revision, UUID> {
}