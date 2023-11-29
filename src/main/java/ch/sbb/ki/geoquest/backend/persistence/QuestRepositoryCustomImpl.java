package ch.sbb.ki.geoquest.backend.persistence;

import java.util.Date;
import java.util.List;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import ch.sbb.ki.geoquest.backend.persistence.entity.Quest;
import ch.sbb.ki.geoquest.backend.persistence.entity.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class QuestRepositoryCustomImpl implements QuestRepositoryCustom {

    private static final double LOCATION_SEARCH_DEFAULT_RADIUS = 0.01; // currently in ° (degree). 1° is 70-120 km in CH

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Quest> findByCriteria(QuestSearchCriteria searchCriteria) {
        QuestSearcher<Quest> searcher = QuestSearcher.selectEntity(entityManager);
        searcher.location(searchCriteria.getLocation(), searchCriteria.getRadius());
        searcher.date(searchCriteria.getDate());
        searcher.onlyNotEnoughResponses(searchCriteria.isOnlyNotEnoughResponses());
        searcher.noResponseFrom(searchCriteria.getNoResponseFromUserId());
        return searcher.getAndBasedQuery().getResultList();
    }

    private static final class QuestSearcher<R> extends BaseCustomSearcher<Quest, R> {

        private QuestSearcher(EntityManager entityManager, Class<R> resultClass) {
            super(entityManager, Quest.class, resultClass);
        }

        static QuestSearcher<Quest> selectEntity(EntityManager entityManager) {
            return new QuestSearcher<>(entityManager, Quest.class);
        }

        static QuestSearcher<Long> selectId(EntityManager entityManager) {
            QuestSearcher<Long> searcher = new QuestSearcher<>(entityManager, Long.class);
            searcher.query.select(searcher.rootEntity.get("id"));
            return searcher;
        }

        void location(Point<G2D> location, Double radius) {
            if (location != null) {
                @SuppressWarnings("rawtypes")
                ParameterExpression<Point> paramLocation = addParameter(Point.class, "position", location);
                Expression<Double> distance = distanceBetween(rootEntity.get("place").get("location"), paramLocation);
                criteria.add(criteriaBuilder.lessThanOrEqualTo(distance, criteriaBuilder.literal(radius != null ? radius : LOCATION_SEARCH_DEFAULT_RADIUS)));
                query.orderBy(criteriaBuilder.asc(distance));
            }
        }

        void date(Date date) {
            if (date != null) {
                criteria.add(or(
                        criteriaBuilder.isNull(rootEntity.get("startDate")),
                        criteriaBuilder.lessThanOrEqualTo(rootEntity.get("startDate"), date)));
                criteria.add(or(
                        criteriaBuilder.isNull(rootEntity.get("endDate")),
                        criteriaBuilder.greaterThanOrEqualTo(rootEntity.get("endDate"), date)));
            }
        }

        void onlyNotEnoughResponses(boolean onlyNotEnoughResponses) {
            if (onlyNotEnoughResponses) {
                criteria.add(criteriaBuilder.greaterThan(
                    rootEntity.get("minResponses"), 
                    count(Response.class, response -> response.join("quest"), quest -> quest.get("id"))
                ));
            }
        }

        void noResponseFrom(Long userId) {
            if (userId != null) {
                Subquery<Response> subquery = query.subquery(Response.class);
                Root<Response> subroot = subquery.from(Response.class);
                subquery.select(subroot).where(criteriaBuilder.and(
                        criteriaBuilder.equal(
                            subroot.get("quest"),
                            rootEntity),
                        criteriaBuilder.equal(
                            subroot.get("user").get("id"), 
                            criteriaBuilder.literal(userId))
                        ));
                criteria.add(criteriaBuilder.not(criteriaBuilder.exists(subquery)));
            }
        }
    }

}
