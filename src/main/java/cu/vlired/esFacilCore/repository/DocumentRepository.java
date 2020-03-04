package cu.vlired.esFacilCore.repository;

import cu.vlired.esFacilCore.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository
    extends JpaRepository<Document, UUID>, CustomDocumentRepository {

}
