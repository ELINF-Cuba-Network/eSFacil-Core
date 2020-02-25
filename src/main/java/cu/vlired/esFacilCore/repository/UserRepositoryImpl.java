package cu.vlired.esFacilCore.repository;

import cu.vlired.esFacilCore.components.I18n;
import cu.vlired.esFacilCore.exception.FieldNotFilterableException;
import cu.vlired.esFacilCore.model.User;
import cu.vlired.esFacilCore.util.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UserRepositoryImpl implements CustomUserRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    I18n i18n;

    private final String[] filterableFields =
            new String[]{"username", "firstname", "lastname", "email"};

    private final String[] sortableFields =
            new String[]{"username", "firstname", "lastname", "email", "active"};

    @Override
    public List<User> list(Page page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);

        Root<User> from = query.from(User.class);

        if (page.getSort() != null) {

            if (Arrays.stream(sortableFields).noneMatch(f -> f.equals(page.getSort()))) {
                throw new FieldNotFilterableException(
                        i18n.t("app.field.not.filterable", ArrayUtils.toArray(page.getSort()))
                );
            }

            String field = User.class.getDeclaredFields()[0].getName();
            Path<Object> path = from.get(page.getSort() != null ? page.getSort() : field);

            Order order;
            if (page.getOrder() != null && page.getOrder().toLowerCase().equals("desc")) {
                order = criteriaBuilder.desc(path);
            } else {
                order = criteriaBuilder.asc(path);
            }

            query.orderBy(order);
        }

        if (!page.getQ().isBlank()) {
            List<Predicate> predicates = new LinkedList<>();
            Arrays
                    .stream(filterableFields)
                    .forEach(f -> predicates.add(criteriaBuilder.like(from.get(f), "%" + page.getQ() + "%")));

            Predicate or = criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
            query.where(or);
        }

        CriteriaQuery<User> select = query.select(from);

        TypedQuery<User> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(page.getPage() * page.getLimit());
        typedQuery.setMaxResults(page.getLimit());

        return typedQuery.getResultList();
    }
}
