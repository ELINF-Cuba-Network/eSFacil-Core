package cu.vlired.esFacilCore.repository;

import cu.vlired.esFacilCore.model.Document;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.util.Page;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class DocumentRepositoryImpl
    implements CustomDocumentRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Document> list(Page page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> query = criteriaBuilder.createQuery(Document.class);

        Root<Document> from = query.from(Document.class);
        CriteriaQuery<Document> select = query.select(from);

        TypedQuery<Document> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(page.getPage() * page.getLimit());
        typedQuery.setMaxResults(page.getLimit());

        return typedQuery.getResultList();
    }

    @Override
    public List<Document> listMe(Page page, User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> query = criteriaBuilder.createQuery(Document.class);

        Root<Document> from = query.from(Document.class);

        Predicate withUser =
            criteriaBuilder.and(criteriaBuilder.equal(from.get("person"), user));
        query.where(withUser);

        CriteriaQuery<Document> select = query.select(from);

        TypedQuery<Document> typedQuery = entityManager.createQuery(select);

        typedQuery.setFirstResult(page.getPage() * page.getLimit());
        typedQuery.setMaxResults(page.getLimit());

        return typedQuery.getResultList();
    }

}
