package ch.sbb.ki.geoquest.backend.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Parameter;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.NonNull;
import org.geolatte.geom.Point;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.lang.Nullable;

/**
 * Base class to implement searcher with custom JPA queries.
 *
 * @param <E> Type of the JPA {@link Entity} to query, defining the (main) FROM.
 * @param <R> Type of the result, may be the entity or its id for example.
 */
public abstract class BaseCustomSearcher<E, R> {

    protected final EntityManager entityManager;
    protected final CriteriaBuilder criteriaBuilder;
    protected final CriteriaQuery<R> query;
    protected final Root<E> rootEntity;
    protected final List<Predicate> criteria;
    protected final List<Consumer<TypedQuery<R>>> postCreateActions;
    private Integer limit;
    private Integer offset;
    private static Boolean isH2 = null;

    public BaseCustomSearcher(@NonNull EntityManager entityManager, @NonNull Class<E> entityClass, @NonNull Class<R> resultClass) {
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
        query = criteriaBuilder.createQuery(resultClass);
        rootEntity = query.from(entityClass);
        criteria = new ArrayList<>();
        postCreateActions = new ArrayList<>();
    }

    /** Combines some conditions / predicate with a logical OR. */
    protected Predicate or(@NonNull Collection<Predicate> restrictions) {
        return criteriaBuilder.or(restrictions.toArray(new Predicate[0]));
    }

    /** Combines some conditions / predicate with a logical OR. */
    protected Predicate or(@NonNull Predicate... restrictions) {
        return criteriaBuilder.or(restrictions);
    }

    /** Combines some conditions / predicate with a logical AND. */
    protected Predicate and(@NonNull Collection<Predicate> restrictions) {
        return criteriaBuilder.and(restrictions.toArray(new Predicate[0]));
    }

    /** Combines some conditions / predicate with a logical AND. */
    protected Predicate and(@NonNull Predicate... restrictions) {
        return criteriaBuilder.and(restrictions);
    }

    /** Condition for an array containing a value. SQL: {@code value = ANY(array)} or for H2: {@code ARRAY_CONTAINS(array, value)}. */
    protected <T> Predicate contains(Expression<Collection<T>> array, Expression<T> value) {
        // see https://stackoverflow.com/questions/72095966/in-h2-database-how-to-use-anyarray
        if (/*isH2()*/true) {
            return criteriaBuilder.equal(criteriaBuilder.function("ARRAY_CONTAINS", Boolean.class, array, value), Boolean.TRUE);
        } else {
            // TODO FPLJS-1241 needs bugfix https://hibernate.atlassian.net/browse/HHH-16312 for Hibernate 6
            // Workaround: ARRAY_CONTAINS defined in Postgres with our Journey-Service postgres_springboot3_manual.sql
            return criteriaBuilder.equal(value, criteriaBuilder.function("ANY", Object.class, array));
        }
    }

    /** Condition for an array containing a value. SQL: {@code value = ANY(array)} or for H2: {@code ARRAY_CONTAINS(array, value)}. */
    protected <T> Predicate contains(Expression<Collection<T>> array, T value) {
        return contains(array, criteriaBuilder.literal(value));
    }

    /**
     * Defines an SQL <code>exists</code> clause, checking the existence of a sub-entity linked to the main entity. 
     * @param <E> The type of the linked sub-entity.
     * @param entityClass The type of the linked sub-entity.
     * @param joinBack Function giving the join from the sub-entity (S) to the main entity (E). 
     * @param fieldForId Function giving the JPA field for joining (comparing equality of the main entity).
     * @return
     */
    protected <S> Predicate exists(Class<S> entityClass, Function<Root<S>, Join<S, E>> joinBack, UnaryOperator<Path<E>> fieldForId) {
        Subquery<S> subquery = query.subquery(entityClass);
        Root<S> subroot = subquery.from(entityClass);
        subquery.select(subroot).where(criteriaBuilder.equal(
                fieldForId.apply(joinBack.apply(subroot)),
                fieldForId.apply(rootEntity)));
        return criteriaBuilder.exists(subquery);
    }

    protected <S> Expression<Long> count(Class<S> entityClass, Function<Root<S>, Join<S, E>> joinBack, UnaryOperator<Path<E>> fieldForId) {
        Subquery<Long> subquery = query.subquery(Long.class);
        Root<S> subroot = subquery.from(entityClass);
        subquery
            .select(criteriaBuilder.count(subroot))
            .where(criteriaBuilder.equal(
                fieldForId.apply(joinBack.apply(subroot)),
                fieldForId.apply(rootEntity)));
        return subquery;
    }

    /** Gives the distance between two points, as computed by the underlying spatial database. */
    @SuppressWarnings("rawtypes")
    protected Expression<Double> distanceBetween(Expression<Point> point1, Expression<Point> point2) {
        return criteriaBuilder.function("ST_Distance", Double.class, point1, point2);
    }

    /** 
     * Limits the result size if <code>limit</code> is non-<code>null</code> and strictly positive. 
     * @see TypedQuery#setMaxResults(int) 
     */
    public void limit(final @Nullable Integer limit) {
        this.limit = limit;
    }

    /** 
     * Offset for the result if <code>offset</code> is non-<code>null</code> and strictly positive. 
     * @see TypedQuery#setFirstResult(int) 
     */
    public void offset(final @Nullable Integer offset) {
        this.offset = offset;
    }

    protected <V> void addParameter(final @NonNull Parameter<V> param, final V value) {
        postCreateActions.add((TypedQuery<R> typedQuery) -> typedQuery.setParameter(param, value));
    }

    /** Adds a parameter of a given class with the given value, returning the parameter as an expression to add in the query. */
    protected <V> ParameterExpression<V> addParameter(Class<V> paramClass, final V value) {
        ParameterExpression<V> param = criteriaBuilder.parameter(paramClass);
        addParameter(param, value);
        return param;
    }

    /** Adds a parameter of a given class with the given value, returning the parameter as an expression to add in the query. */
    protected <V> ParameterExpression<V> addParameter(Class<V> paramClass, String paramName, final V value) {
        ParameterExpression<V> param = criteriaBuilder.parameter(paramClass, paramName);
        addParameter(param, value);
        return param;
    }

    /** Gives an executable query with all criteria AND-ed. */
    public TypedQuery<R> getAndBasedQuery() {
        query.where(and(criteria));
        TypedQuery<R> typedQuery = entityManager.createQuery(query);
        if (limit != null && limit >= 0) {
            typedQuery.setMaxResults(limit);
        }
        if (offset != null && offset > 0) {
            typedQuery.setFirstResult(offset);
        }
        for (Consumer<TypedQuery<R>> postCreateAction : postCreateActions) {
            postCreateAction.accept(typedQuery);
        }
        return typedQuery;
    }

    protected boolean isH2() {
        if (isH2 == null) {
            isH2 = getDialect().toLowerCase().contains("h2"); // NOSONAR just setting one Boolean is atomic
        }
        return isH2;
    }

    protected String getDialect() {
        final Session session = (Session) entityManager.getDelegate();
        final SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
        final Dialect dialect = sessionFactory.getJdbcServices().getDialect();
        return dialect.toString();
    }
}
