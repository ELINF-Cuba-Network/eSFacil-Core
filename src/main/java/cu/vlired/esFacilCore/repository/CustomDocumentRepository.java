package cu.vlired.esFacilCore.repository;

import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.util.Page;

import java.util.List;

public interface CustomDocumentRepository {
    List<Document> list(Page page);
    List<Document> listMe(Page page, User user);
}
